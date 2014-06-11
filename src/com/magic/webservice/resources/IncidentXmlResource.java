package com.magic.webservice.resources;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

import com.magic.webservice.api.itsm.Incident;
import com.magic.webservice.api.itsm.IncidentCache;
import com.magic.webservice.api.itsm.XML;
import com.magic.webservice.util.Constants;
import com.magic.webservice.util.UIDGenerator;

/**
 * jMagic Incident Webservice,
 * allows you to send incident object
 * 
 * @author Jackie Shi
 * @version 1.0
 */
//Sets the path to base URL + /event
@Path("/incidentxml")
public class IncidentXmlResource {
	
	static Logger logger = Logger.getLogger(Constants.log4jPrefix+IncidentXmlResource.class.getName());
	 
	
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
      @Produces(MediaType.TEXT_HTML)
	  public String getStats()
	  {
		  IncidentCache cache = IncidentCache.getInstance();
		  return cache.getStats();
	  }
	  
	  	 
	  
	  /** PUT method: new incident with xml as upper level element */
	  @PUT
	  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public String newIncidentXml(JAXBElement<XML> xml) {
	    XML  incidentXml= xml.getValue();
	    //String uuid = UIDGenerator.getUID(type);
	    if (incidentXml !=null && incidentXml.getIncident() !=null)
	    {
	    	Incident incident  = incidentXml.getIncident(); 
	    	String msg = "received new incident "+ incident.getNumber();
	    	 IncidentCache cache = IncidentCache.getInstance();
	    	 cache.addToCache(incident);
	    	 logger.info(msg);
	    	 return msg;
	    	
	    }else throw new RuntimeException("not a valid incident input");
	  }
	  
	  
	  /** POST method: update incident */
	  @POST
	  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public String updateIncidentXml(JAXBElement<XML> xml) {
	    XML  incidentXml= xml.getValue();
	    //String uuid = UIDGenerator.getUID(type);
	    if (incidentXml !=null && incidentXml.getIncident() !=null)
	    {
	    	Incident incident  = incidentXml.getIncident();  
	    	String msg = "received updated incident "+ incident.getNumber();
	    	 IncidentCache cache = IncidentCache.getInstance();
	    	 cache.updateIncident(incident);
	    	 logger.info(msg);
	    	 return msg;
	    	
	    }else throw new RuntimeException("not a valid incident input");
	  }
	  
	  @DELETE
	  public void deleteEvent() {
	    
	      throw new RuntimeException("Delete: event  not found");
	  }

}




