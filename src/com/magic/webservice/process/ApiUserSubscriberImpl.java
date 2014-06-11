package com.magic.webservice.process;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.magic.api.jMagicObject;
import com.magic.api.constants.ObjectUpdateAction;
import com.magic.api.subscribableobjects.jIncidentTicket;
import com.magic.api.subscribableobjects.jMagicUser;
import com.magic.api.util.SecurityToken;
import com.magic.tomcat.TomcatImpl;
import com.magic.tomcat.TomcatObjectSubscriber;
import com.magic.tomcat.listener.ApiUserSubscriberListener;
import com.magic.webservice.api.CachedObjects;
import com.magic.webservice.util.Constants;

public class ApiUserSubscriberImpl implements  ApiUserSubscriberListener{

	static Logger logger = Logger.getLogger(Constants.log4jPrefix+ApiUserSubscriberImpl.class.getName());
	   
	  
	@Override
	public void syncCompleted() {
		
		logger.info("sync complete");
		TomcatImpl tomcatImpl = TomcatImpl.getInstance();
		TomcatObjectSubscriber apiSubscriber = tomcatImpl.getApiUserSubscriber();
		if (apiSubscriber !=null)
		{
			Map objectMap = apiSubscriber.getObjectMap();
			//add all tokens from jMagicUser to one big treemap
			TreeMap<String, SecurityToken> allTokens = new TreeMap<String, SecurityToken>();
			if (objectMap !=null && objectMap.size() > 0)
			{								
				synchronized (objectMap) 
				{
					Iterator it = objectMap.values().iterator();
					while (it.hasNext())
					{
						try{
							jMagicObject jso = (jMagicObject)it.next();
							if (jso instanceof jMagicUser)
							{
								jMagicUser user = (jMagicUser)jso;
								TreeMap<String, SecurityToken> map = user.getSecurityTokenMap();
								if (map !=null && map.size() > 0 ){
									logger.info("user has security tokens "+user.getApiUserName());
									allTokens.putAll(map);
								}
							}
							
						}catch (Exception e) {
							logger.error("faield to add user", e);
						}
					}
				}
				
				//now process
				addTokenMap(allTokens);
			}						
		}
		
	}

	/**
	 * Add to local cache
	 * @param allTokens
	 */
	private void addTokenMap(TreeMap<String, SecurityToken> allTokens)
	{
		if (allTokens !=null && allTokens.size() > 0)
		{
			Iterator<SecurityToken> itr = allTokens.values().iterator();
			CachedObjects cache = CachedObjects.getInstance();
			while (itr.hasNext())
			{
				//add to cache, but don't need to update jMagicServer
				SecurityToken token = itr.next(); 
				logger.info(token.toString());
				cache.addUser(token, false);
			}					
		}
	}

	@Override
	public void created(jMagicObject object) 
	{
		if (object !=null && object instanceof jMagicUser)
		{
			jMagicUser user = (jMagicUser)object;
			addTokenMap(user.getSecurityTokenMap());
		}
		
	}

	@Override
	public void deleted(String objectId, String userName, Date timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updated(jMagicObject object) {
		if (object !=null && object instanceof jMagicUser)
		{
			jMagicUser user = (jMagicUser)object;
			addTokenMap(user.getSecurityTokenMap());
		}
		
	}

	@Override
	public void updatedAttributes(String objectId, String userName,
			Date timestamp, SortedMap updates, ObjectUpdateAction updateAction,
			HashSet eventIds) {
		// TODO Auto-generated method stub
		
	}
	

}
