package com.fidelity.magic.webservice.resources;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import com.fidelity.magic.api.jMagicEvent;
import com.fidelity.magic.api.constants.DuplicateChecker;
import com.fidelity.magic.api.constants.EventSeverity;
import com.fidelity.magic.api.util.SecurityToken;
//import com.fidelity.magic.tomcat.TomcatImpl;
import com.fidelity.magic.tomcat.TomcatImpl;
import com.fidelity.magic.webservice.api.CachedObjects;
import com.fidelity.magic.webservice.api.Event;
import com.fidelity.magic.webservice.api.EventResult;
import com.fidelity.magic.webservice.authentication.MagicAuthentication;
import com.fidelity.magic.webservice.authentication.jMagicUnauthorizedException;
import com.fidelity.magic.webservice.util.Constants;
import com.fidelity.magic.webservice.util.UIDGenerator;

/**
 * jMagic Event Webservice,
 * allows you to publish, update and get an event.
 * 
 * @author Jackie Shi
 * @version 1.0
 * @since 10/10/2013
 */
//Sets the path to base URL + /event
@Path("/event")
public class EventResource {
		
	static Logger logger = Logger.getLogger(Constants.log4jPrefix+EventResource.class.getName());
    
	private static final String type = "EVENT";
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  
	  /*String id;
	  public EventResource(UriInfo uriInfo, Request request, String id)
	  {
		  this.uriInfo = uriInfo;
		  this.request = request;
		  this.id = id;
	  }*/
	  
	  /** GET method: get detailed event information for a specific event */
	  @GET
     @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  //@Produces(MediaType.APPLICATION_JSON)
	  public Event getEvent() {
	    Event event = retrieveEventFromCache(); //search event from cache
	    if(event == null)
	      throw new RuntimeException("Get: event not found");
	    return event;
	  }
	  
	  /** GET method: For the browser test*/
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public Event getEventHtml() {
		    Event event = retrieveEventFromCache(); //search event from cache
		    if(event == null)
		      throw new RuntimeException("Get: event not found");
		    return event;
	 }
	  
	  /** PUT method: Create a jMagic event. only take JSON since we use Jackson POJO mappinag by passing 
	   * JAXB*/
	  @PUT
	  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public EventResult putEvent(Event event, @Context HttpHeaders header) 
	  {
	    SecurityToken secToken = null;
		MultivaluedMap<String, String> map = header.getRequestHeaders();
		if (map !=null && map.size() >0)
		{
			String token = map.getFirst(LoginResource.HEADER_TOKEN);
			CachedObjects cache = CachedObjects.getInstance();
	        if (!cache.hasToken(token))
	        	throw new jMagicUnauthorizedException(LoginResource.TOKEN_ERROR_MSG);
	        
	        secToken = cache.getCachedToken(token);
		}
	    //publish
	    String uuid =   publishEvent(event, secToken);
	    EventResult result = new EventResult();
	    result.setUuid(uuid);
	    
	    return result;
	  }
	  
	  
	  @DELETE
	  public void deleteEvent() {
	    
	      throw new RuntimeException("Delete: event  not found");
	  }
	  
	  /** for browser testing purpose to publish an event */
	  @POST
	  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public EventResult newEvent 
	  		(@FormParam("objectName") String primaryTarget,
	  				@FormParam("eventClass") String secondaryTarget,
	  				@FormParam("eventText") String eventText,
	  				@FormParam("severity") String severity,
	  				@FormParam("host") String host,
	  				@Context HttpServletResponse servletResponse) throws IOException 
	  {
		Event event = new Event();
	    event.setObjectName(primaryTarget);
	    event.setEventClass(secondaryTarget);
	    event.setSeverity(severity);
	    event.setEventText(eventText);
	    event.setHost(host);
	    
	    //publish event
	    String uuid = publishEvent(event, null);
	    //servletResponse.sendRedirect("../create_event.html");
	    EventResult result = new EventResult();
	    result.setUuid(uuid);
	    
	    return result;
	  }
	  

	  
	  //TODO
	  private Event retrieveEventFromCache()
	  {
		  //hard code
		  Event e = new Event();
		  SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
		  String id = format.format(new Date());
		 
		  e.setEventText("hard coded event text");
		  e.setObjectName("example-objectName");
		  e.setEventClass("example-eventClass");
		  e.setSeverity("critical");
		  e.setHost("ldal643");
		  
		  Map<String, String> attributeMap = new TreeMap<String, String>();
		  attributeMap.put("name1", "value1");
		  attributeMap.put("name2", "value2");
		  attributeMap.put("name3", "value3");
		  
		  e.setNameValuePairs(attributeMap);
		  return e;
	  }
	  
	  /**
	   * Per Tom,
	   * Call jMagicServer to publish event directly
	   * without use a deliverQ
	   * @param event
	   * @return
	   */
	  private String publishEvent(Event event, SecurityToken secToken)
	  {
		 		  
		  if (event !=null)
		  {
			  String uuid = UIDGenerator.getUID(type);
				
			  //severity
			  EventSeverity severity = EventSeverity.CRITICAL;
			  String sev = event.getSeverity();
			  if (sev !=null)
			  {
				 if (EventSeverity.CRITICAL.toString().equalsIgnoreCase(sev))
					 severity = EventSeverity.CRITICAL;
				 else if (EventSeverity.WARNING.toString().equalsIgnoreCase(sev))
					 severity = EventSeverity.WARNING;
				 else if (EventSeverity.RESOLUTION.toString().equalsIgnoreCase(sev))
					 severity = EventSeverity.RESOLUTION;
			  }
			  
			  
			  TreeMap nvps = new TreeMap();
			  if (event.getNameValuePairs() !=null)
				  nvps.putAll(event.getNameValuePairs());
			  
			  nvps.put(jMagicEvent.PUBLISHED_UUID, uuid);
			  nvps.put(jMagicEvent.PUBLISH_TO_PARTNER, "true");
			  String username = "";
			  String usermode = "";
			  if (secToken !=null)
			  {
				  username = secToken.getCorpId();
				  usermode = secToken.getUsermode();
				  
				  nvps.put("usermode", usermode);
				  nvps.put("serviceaccount", username);
			  }
			  //use category
			  String category = event.getCategory();
			  //if not passed by caller, derive from severity
			  if (category == null){				  
				  if (severity.toString().equals(EventSeverity.RESOLUTION))
					  category = "ok";
				  else category = "failed";				  
			  }
				  			  
			  
			  TomcatImpl tomcat = TomcatImpl.getInstance();
			  tomcat.getEventPublisher().publish("REST", 
					  	event.getEventText(), 
					  	severity, 
					  	event.getObjectName(), 
					  	event.getEventClass(), 
					  	category,
					  	event.getHost(), 
					  	DuplicateChecker.COMPARE_ATTRIBUTE_ALWAYS_UPDATE, 
					  	event.getOpdoc(), nvps);  
			   
			  logger.info(username+ " in usermode " +usermode +" published event: "+event.toString());
			  return uuid;
		  }else return null;
		  
		  
	  }
	  
	  /*
	  private Response putResponse(Event event) 
	  {
		    Response res;
		    // res = Response.noContent().build();
		    
		    res = Response.created(uriInfo.getAbsolutePath()).build();
		    
		    //publish
		    publishEvent(event);
		    
		    return res;
	  }*/
}




