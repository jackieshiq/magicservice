package com.magic.webservice.process;


import org.apache.log4j.Logger;

import com.magic.tomcat.TomcatEventSubscriber;
import com.magic.tomcat.TomcatImpl;
import com.magic.tomcat.TomcatObjectSubscriber;
import com.magic.webservice.util.Constants;

public class MagicServiceMain implements Runnable {
	
	private ApiUserSubscriberImpl apiListener = null;
	private static Logger logger = Logger.getLogger(Constants.log4jPrefix+MagicServiceMain.class.getName());
    
	public MagicServiceMain()
	{
		apiListener = new ApiUserSubscriberImpl();
	}
	
	//run loop, 
	public void run()
	{
		registerListenersWithTomcat();
		//you may put forever loop here later.
	}
	
	/**
	 * must register with TomcatSubscriber to listen to Object actions
	 */
	public void registerListenersWithTomcat()
	{
		logger.info("begin");
		//register listeners	
		try{
			//wait for Tomcat start up
			while (true)
			{
				if (TomcatImpl.getInstance().isTomcatSubscriberRunning())
					break;
				else Thread.sleep(5000);
				
				logger.warn("waiting... isTomcatSubscriberRunning flag is false");
			}
					
			//wait for Tomcat jMagicUser object sync complete
			while (true)
			{
				TomcatObjectSubscriber apiUserStub  = TomcatImpl.getInstance().getApiUserSubscriber();			
				if (apiUserStub !=null && apiUserStub.isSyncComplete())
					break;
				else Thread.sleep(5000);
				
				logger.warn("waiting... jMagicUser objects sync not complete");
			}
							
		}catch (Exception e) 
		{
			logger.error("", e);
		}
		
		logger.info("about to register with Tomcat");
		TomcatImpl.getInstance().registerObjectListener(apiListener);
		logger.info("end");
	}
	
	public void unregisterListenersFromTomcat()
	{
		//		unregister listeners	
		logger.info("begin");
		TomcatImpl.getInstance().unregisterObjectListener(apiListener);
		logger.info("end");
	}
	

}
