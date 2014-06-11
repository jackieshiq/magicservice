package com.magic.webservice.api.itsm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;


import com.magic.api.stats.Counter;
import com.magic.api.stats.Gauge;
import com.magic.api.stats.StatisticsManager;

import com.magic.webservice.util.Constants;

/**
 * singleton class hold all incident data
 * @author A322448
 *
 */
public class IncidentCache {
	
	static Logger logger = Logger.getLogger(Constants.log4jPrefix+IncidentCache.class.getName());
	   
	private Map<String, Incident>  incidentMap;
	private ArrayList<Method> getters; 
	
	
	//Capture stats
	private Gauge imCacheSize;
	private Counter createCounter;
    private Counter updateCounter;
    
    
	private static IncidentCache cache;	
	private  IncidentCache()
	{		
		incidentMap = Collections.synchronizedMap(new TreeMap<String, Incident>());
		
		intStatistics();
		Method[] allMethods = Incident.class.getMethods();
		getters = new ArrayList<Method>();
		for(Method method : allMethods) {
		    if(method.getName().startsWith("get")) {
		    	getters.add(method);
		    }
		}
	}
	
	private void intStatistics()
    {
        try{
            imCacheSize = new Gauge("SRVNOW_IMCacheSize", "IM cache size", 60);
            
            createCounter = new Counter("SRVNOW_IM_Create_counter", "Create call received", 60 );
            updateCounter = new Counter("SRVNOW_IM_Update_counter", "Update call received", 60 );
            
            // tell the statistics manager to start writing to the database
           // StatisticsManager.beginRecording("jNotification", TomcatImpl.getInstance().getServerClient().getMagicServer());
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
	public static IncidentCache getInstance()
	{
		if ( cache == null) 
			cache = new IncidentCache();
		return cache;
	}
	
	public void incrementCreate()
	{
		this.createCounter.increment();
	}
	
	public void incrementUpdate()
	{
		this.updateCounter.increment();
	}
	
	public String getStats()
	{
		StringBuffer  buffer = new StringBuffer("<html><body>");

		
	      buffer.append("<table border=1>\n");
	      buffer.append("<caption align=left><b>Incident POC Counters</b></caption>");
	      
	      buffer.append("<tr>");
	      buffer.append("<th>name</th>");
	      buffer.append("<th>recent count</th><th>today count</th><th>total count</th>");
	      buffer.append("<th>last update</th>");
	      buffer.append("</tr>\n");

	      buffer.append(printCounter(createCounter));
	      buffer.append(printCounter(updateCounter));
	      
	      buffer.append("</table>");
	      buffer.append("<br><br>");
	    
	      buffer.append("<p>Total count: "+this.incidentMap.size());
	      
	      if (incidentMap !=null && incidentMap.size() >0)
	      {
	    	  Iterator<String> it = incidentMap.keySet().iterator();
	    	  while (it.hasNext())
	    	  {
	    		  buffer.append("<p>"+it.next() );
	    	  }
	      }
	      buffer.append("</body></html>");
	      return buffer.toString();

	}

	private String printCounter(Counter counter)
	{
		StringBuffer  buffer = new StringBuffer();
		buffer.append("<tr>");
       
  
        buffer.append("<td>" + counter.getName() + "</td>");
        
        buffer.append("<td>" + counter.getIntervalCount() + "</td>");
        buffer.append("<td>" + counter.getTodayCount() + "</td>");
        buffer.append("<td>" + counter.getCount() + "</td>");
 

        if(counter.getLastUpdateTime().getTime() > 0) 
          buffer.append("<td>" + counter.getLastUpdateTime() + "</td>");
        else buffer.append("<td>&nbsp;</td>");

        
        buffer.append("</tr>\n");
        
        return buffer.toString();
	}
	
	public void addToCache(Incident incident)
	{
		if (incident !=null )
		{
			String number =  incident.getNumber();
			if (number != null && number.trim().length() > 0){
				incidentMap.put(number, incident);	
				incrementCreate();
				logger.info("Added incident "+number);
			}
		}
	}
	
	public void updateIncident(Incident incident)
	{
		if (incident !=null )
		{
			String num= incident.getNumber();
			Incident localIncident = getIncident(num);
			 if (localIncident == null)
			 {
				logger.warn("cannot update incident, no such incident in cache " + num);
				addToCache(localIncident);	

			 }else{
				 
				 //update(localIncident, incident);
				 localIncident.update(incident);
				 incrementUpdate();
				 logger.info("updated incident "+ num);
			 }

		}
	}
	
	//TODO
	private void update(Incident oldObj, Incident newObj)
	{
		
	}
	
	public void removeFromCache(String number)
	{
		if (number != null && number.trim().length() > 0)
		{
			if (incidentMap.remove(number) !=null)
				logger.info("Deleted incident "+number);
			else
				logger.warn("No such incident to delete "+number);
		}
	}
	
	public Incident getIncident(String number)
	{
		Incident in = null;
		if (number != null && number.trim().length() > 0)
		{
			in =  incidentMap.get(number);
			if ( in !=null)
				logger.info("Got incident "+number);
			else
				logger.warn("No such incident "+number);
		}
		
		return in;
	}
	
	public Map<String, Incident> getAllIncident()
	{
		return incidentMap;
	}

}
