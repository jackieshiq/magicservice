package com.fidelity.magic.webservice.api;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.fidelity.magic.api.jMagicObject;
import com.fidelity.magic.api.constants.Hint;
import com.fidelity.magic.api.subscribableobjects.jMagicUser;
import com.fidelity.magic.api.util.SecurityToken;
import com.fidelity.magic.tomcat.TomcatImpl;
import com.fidelity.magic.tomcat.TomcatObjectSubscriber;
import com.fidelity.magic.webservice.authentication.MagicAuthentication;
import com.fidelity.magic.webservice.authentication.jMagicUnauthorizedException;
import com.fidelity.magic.webservice.util.Constants;

/**
 * singleton class for caching Tokens for now, will be re-implemented using Shiro security
 * @author A322448
 *
 */
public class CachedObjects {
	
   static Logger logger = Logger.getLogger(Constants.log4jPrefix+CachedObjects.class.getName());
	   
	private static int TOKEN_EXPIRATION_HOURS = 24;
	private static int TOKEN_CLEANUP_HOURS = 1; //clean up token cache every 1 hour
	private static CachedObjects cache =  new CachedObjects();
	
	/** stores all tokenMap from user logins, and from jMagicServer jMagicUeer
	 *  The idea is the local copy always has the latest token
	 */
	private TreeMap<String, SecurityToken> tokenMap;	
	
	private CachedObjects()
	{
		tokenMap = new TreeMap<String, SecurityToken>();		
		logger.info("initialized");
		
		//check for expired token every 12 hours
		Timer myTimer = new Timer();
		myTimer.scheduleAtFixedRate(new TimerTask() {			
			@Override
			public void run() 
			{
				cleanupExpiredTokens();
			}
		
		}, 5 * 60 * 1000L,  TOKEN_CLEANUP_HOURS * 60 *60 *1000L ); //delay 5 minutes, run every 12 hours
		
	}
	
	/**
	 * remove expired token object from jMgicUser
	 */
	private void cleanupExpiredTokens()
	{
		logger.info("about to clean up expired Tokens");
		try{
			
			if (tokenMap !=null && tokenMap.size() > 0 )
			{
				
				logger.info("total size of tokens:"+ tokenMap.size());
				Iterator<SecurityToken> it = tokenMap.values().iterator();
				while (it.hasNext())
				{
					SecurityToken token = it.next();
					//log cached tokens every 12 hours
					if (token !=null)
						logger.info(token.toString());
					
					if (token !=null && token.hasExpired(TOKEN_EXPIRATION_HOURS))
					{						
						updateAPIUser(token, Hint.REMOVE_SECURITY_TOKEN);
						it.remove(); //remove from local cache too
					}
				}
			}
			
		}catch(Exception e)
		{
			logger.error(e);
		}
	}

	public static CachedObjects getInstance()
	{		
		return cache;
	}
	
	
	/**
	 *  This method is called when user calls Login API, as well as when we get api updated call
	 *  from jMagicServer
	 * 1) When user logs in REST API, we'll add a security token in the local cache
	 * right away, so that the subsequent API call to publish event can find the token
	 * then we'll update jMagicServer service account API User.
	 * 2) if we already have this token in local cache, we'll take the token object with the latest log in 
	 * time because some API user may log in every time they publish an alert which will result in the same token
	 * 
	 * @param token
	 * @param updateMagic - when user calls Login REST API, we'll update jMagicServer
	 * @See com.fidelity.magic.api.util.SecurityToken
	 */
	public void addUser(SecurityToken token, boolean updateMagic) throws jMagicUnauthorizedException
	{
		if (token !=null && token.getToken() !=null)
		{
			//do we already have this token?
			SecurityToken t = tokenMap.get(token.getToken());
			if (t != null)
			{
				boolean b = t.update(token); //we have it, update login time if necessary
				if (b)
					logger.info(String.format("updated token login time in cache: %s for user %s", token.getToken(), token.getCorpId()));
							
			}else{
				
				//not in cache, add to local cache
				synchronized (tokenMap) {
					tokenMap.put(token.getToken(), token);
					logger.info(String.format("added token to cache: %s for user %s", token.getToken(), token.getCorpId()));
				}
				
			}			

			//update jMagicServer to add token
			if (updateMagic)
			{
				updateAPIUser(token, Hint.ADD_SECURITY_TOKEN);
			}
			
		}
			
	}
	
	/**
	 * call jMagicServer to update jMagicUser
	 * 
	 * @param token
	 * @param hint: Hint.ADD_SECURITY_TOKEN or Hint.REMOVE_SECURITY_TOKEN
	 */
	private void updateAPIUser(SecurityToken token, Hint hint)
	{
		try{
			if (token !=null && hint !=null)
			{
				TomcatImpl tomcat = TomcatImpl.getInstance();
				TomcatObjectSubscriber apiSubscriber = tomcat.getApiUserSubscriber();
				//find jMagicUser from cache based on user mode
				
				jMagicUser apiUser = getAPIUserByName(token.getUsermode());
				if (apiUser == null) throw new Exception("no such jMagicUser  " + token.getUsermode()); 
				//only one entry in security map
				TreeMap<String, SecurityToken> securityTokenMap = new TreeMap<String, SecurityToken>();	
				securityTokenMap.put(token.getToken(), token);
				
				apiUser.setSecurityTokenMap(securityTokenMap);
				apiUser.setUpdateHint(hint); //Hint.ADD_SECURITY_TOKEN or Hint.REMOVE_SECURITY_TOKEN
				apiSubscriber.getMagicService().update(apiSubscriber, apiUser);
				
				logger.info(hint.toString() +": jMagicServer API user token map for username " + token.toString());				
			}
			
		}catch(Exception e)
		{
			logger.error(e);
		}
	}

	private jMagicUser getAPIUserByName(String usermode)
	{
		
		TomcatImpl tomcat = TomcatImpl.getInstance();
		TomcatObjectSubscriber apiSubscriber = tomcat.getApiUserSubscriber();
		//find jMagicUser from cache based on user mode
		Map usermap =  apiSubscriber.getObjectMap();
		if (usermap !=null)
		{
			Iterator it = usermap.values().iterator();
			while (it.hasNext())
			{
				jMagicObject jso = (jMagicObject)it.next();
				//logger.info(jso.getClass().getName() +": "+jso.getObjectId());
				if (jso instanceof jMagicUser)
				{
					jMagicUser apiUser = (jMagicUser)jso;
					if (usermode !=null && apiUser !=null && apiUser.getApiUserName().equalsIgnoreCase(usermode))
					{
						return apiUser;
					}	
				}
										
			}
			
		}
		
		return null;
	}
	
	public boolean hasToken(String token)
	{
		//check local cache
		if (tokenMap.containsKey(token))
		{
			//expired?
			SecurityToken secToken = tokenMap.get(token);
			if (secToken !=null)
				return !secToken.hasExpired(24);
				
		}
		
		
		return false;
	}
	
	
	public SecurityToken getCachedToken(String token)
	{
		//check local cache
		if (tokenMap.containsKey(token))
		{
			//expired?
			SecurityToken secToken = tokenMap.get(token);
			if (secToken !=null && !secToken.hasExpired(TOKEN_EXPIRATION_HOURS))
				return secToken;
				
		}
				
		return null;
	}
}
