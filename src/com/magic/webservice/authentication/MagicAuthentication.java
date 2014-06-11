package com.magic.webservice.authentication;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeSet;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

import com.magic.webservice.api.CachedObjects;
import com.magic.webservice.resources.LoginResource;
import com.magic.webservice.util.*;
import com.magic.api.authentication.jMagicEncrypter;
import com.magic.api.subscribableobjects.jMagicUser;
import com.magic.api.util.SecurityToken;
import com.magic.tomcat.TomcatImpl;
import com.magic.util.DebugFilter;

/**
 * wrapper of LDAPAuthentication
 * @author A322448
 *
 */
public class MagicAuthentication
{

    static Logger logger = Logger.getLogger(Constants.log4jPrefix+MagicAuthentication.class.getName());
    
    private SecurityToken securityToken;
	private String encryptedPassword;
   	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	/**
	 * authenticate service account via VDS
	 *  
	 * @param corpId
	 * @param password
	 * @param usermode
	 * @param requireEncryption, allow caller to pass un-encrypted password
	 * @return
	 * @throws jMagicUnauthorizedException
	 */
	public static SecurityToken getSecurityToken(String corpId, String password, String usermode, boolean requireEncryption) 
			throws jMagicUnauthorizedException
	{
				
		SecurityToken securityToken = null;
		if (corpId == null || corpId.trim().length() == 0 
				||password == null || password.trim().length() == 0 )
			throw new jMagicUnauthorizedException(LoginResource.UNAUTH_ERROR_MSG);
		
	
		logger.info(String.format("API User detail coprID=%s, usermode=%s, pw length=%s", 
				corpId, usermode, password.trim().length()));
		
		//encrypt password as jMagicServer needs it
		if (requireEncryption)
		{
			jMagicEncrypter encrypter = new jMagicEncrypter();
			password =  encrypter.encrypt(password);
		}
		
		//authorization first for user mode
		String validUsermode = getValidUserMode(corpId, usermode);
		if (validUsermode == null || validUsermode.trim().length() == 0 )
		{
			logger.error("log in failed, couldn't find valid user mode for "+corpId+", logged in with user mode "+usermode);
			throw new jMagicUnauthorizedException(LoginResource.NOT_AUTHORIZED_MSG);
		}
		else usermode = validUsermode;
			//LDAP authentication
		try{	
			if (isValidLogin(corpId, password))
			{
				securityToken = new SecurityToken(corpId, usermode);			
				String token = SecurityToken.generateToken(corpId + password + usermode + dateFormat.format(new Date()));
				logger.info("got token " +token);
								
				if (token != null && token.trim().length() > 0)
				{
					
					securityToken.setToken(token);
					securityToken.setLoginTime(new Date());				
					//add to cache
					CachedObjects cache = CachedObjects.getInstance();
					cache.addUser(securityToken, true);
					logger.info(String.format("generated token  %s for user %s",token, corpId));
				}					
			}
		}catch(Exception e)	
		{
			logger.error(e.getMessage(), e);
			throw new jMagicUnauthorizedException(e.getMessage());
		}
		
		return securityToken;
	}
	
	/**
	 * Ask jMgicServer to authenticate this user
	 * @return
	 */
	private static boolean isValidLogin(String corpId, String encryptedPassword) throws Exception
	{
		TomcatImpl impl = TomcatImpl.getInstance();
		boolean b = impl.authenticate(corpId, encryptedPassword);
		if (b)
			logger.info(String.format("authenticated user %s", corpId));
		else 
			logger.error(String.format("invalid login for user %s", corpId));
		return b;
		
	}
	
	/** 
	 * validate user authorization
	 * 1) user may or may not provide a user mode
	 * 2) if user name is a corp. ID, get user mode from VDS
	 * 3) if user name is a service account, get usermode from jMagicUser, else return 401
	 * 
	 * @return a valid user mode, or null
	 */
	private static String getValidUserMode(String username, String usermode) throws jMagicUnauthorizedException
	{
		
		String validUserMode = null;
		try{
			TomcatImpl impl = TomcatImpl.getInstance();
			// if user name is corpID or service account
			if (username != null && username.trim().length() > 0)				
			{				
				if (username.matches("[aA][0-9][0-9][0-9][0-9][0-9][0-9]"))
				{					
					logger.info("about to authorize corp ID " +username);
					//get VDS security groups
					HashSet set = impl.fetchSecurityGroups(username);
					if (set != null)
					{
						//take the VDS auto groups, get Magic User modes;
						TreeSet<String> set2 = impl.getMagicUsersFromVDSGroups(set);
						if (set2 != null && set2.size() > 0)
						{
							//user logs in API without a usermode, get the first one from list
							if (usermode == null || usermode.trim().length() == 0)
							{
								validUserMode = set2.first();	
							}else if (set2.contains(usermode))
							{
								//if user logs in with a usermode, makes sure it is in the list
								validUserMode = usermode;
							}
						}					
					}
				}else //service account
				{
					logger.info("about to authorize service account " +username);
					//get a list of jMagicUser 
					TreeSet<String> allUsers = impl.getMagicUsersbyCorpId(username);					
					//found it
					if (allUsers != null && allUsers.size()  > 0 )
					{
						logger.info("Found at least one jMagicUser for service account " +username);
						//user logs in API without a usermode, get the first one from list
						if (usermode == null || usermode.trim().length() == 0)
						{
							validUserMode = allUsers.first();	; //TODO, not sure about this							
							logger.info("Service account logs in without usermode, pick the first usermode "+validUserMode+ " from jMserver for service account " +username);
							
						}else if (allUsers.contains(usermode))
						{							
							//loop through the result to get the matching user mode
							validUserMode = usermode;
							logger.info("Service account logs in with a valid user mode usermode "+ username);
														
						}						
					}
					
				}			
	
			}
		}catch(Exception e)
		{
			logger.error("failed getting user mode for "+username+" due to "+ e.getMessage(), e);
			throw new  jMagicUnauthorizedException(e.getMessage());
		}
		
		return validUserMode;		
	}

}
