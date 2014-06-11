package com.fidelity.magic.webservice.resources;

import java.io.IOException;
import java.text.SimpleDateFormat;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import com.fidelity.magic.api.constants.DuplicateChecker;
import com.fidelity.magic.api.constants.EventSeverity;
import com.fidelity.magic.webservice.api.Event;
import com.fidelity.magic.webservice.api.EventResult;
import com.fidelity.magic.webservice.api.itsm.Incident;
import com.fidelity.magic.webservice.api.itsm.IncidentCache;
import com.fidelity.magic.webservice.api.itsm.XML;
import com.fidelity.magic.webservice.util.Constants;
import com.fidelity.magic.webservice.util.UIDGenerator;

/**
 * jMagic Incident Webservice,
 * allows you to send incident object
 * 
 * @author Jackie Shi
 * @version 1.0
 */
//Sets the path to base URL + 
@Path("/incident/v1")
public class IncidentResourceV1 {
	
	static Logger logger = Logger.getLogger(Constants.log4jPrefix+IncidentResourceV1.class.getName());
	 
	
	/**
	 * used for creating UUID
	 */
	private static final String type = "INCIDENT";
	
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  
	  
	  
	  /** GET method: get detailed Incident information for a specific Incident */
	  @GET
      @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public Incident getIncident(@QueryParam("number") String number)
	  {
		  IncidentCache cache = IncidentCache.getInstance();
		  Incident in = cache.getIncident(number);
		  
		  if(in ==null)
			  throw new RuntimeException("Get: event not found");
		  
		  return in;
	  }
	  
	  
	  /** PUT method: new incident */
	  @PUT
	  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public String newIncident(JAXBElement<Incident> inputIncident) {
	    Incident  incident= inputIncident.getValue();
	    //String uuid = UIDGenerator.getUID(type);
	    if (incident !=null)
	    {
	    	 String msg = "jMagic received new incident "+ incident.getNumber();
	    	 IncidentCache cache = IncidentCache.getInstance();
	    	 cache.addToCache(incident);
	    	 logger.info(msg);
	    	 return msg;
	    	
	    }else throw new RuntimeException("not a valid incident input");
	  }
	  
	  
	  
	  
	  /** POST method: update incident */
	  @POST
	  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public String updateIncident(JAXBElement<Incident> inputIncident) 
	  {
		  StringBuilder sb= new StringBuilder("received updated incidents: ");
		
			Incident  incident= inputIncident.getValue();
		    //String uuid = UIDGenerator.getUID(type);
		    if (incident !=null)
		    {
		    	 sb.append(incident.getNumber()+ " ");
		    	 IncidentCache cache = IncidentCache.getInstance();
		    	 cache.updateIncident(incident);	    	 
		    	
		    } else throw new RuntimeException("not a valid incident input");					
		 
	    
		  logger.info(sb.toString());
		  return  sb.toString();
	  }
	  

	  
	  @DELETE
	  public void deleteEvent() {
	    
	      throw new RuntimeException("Delete: event  not found");
	  }

}




