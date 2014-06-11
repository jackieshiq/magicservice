package com.fidelity.magic.webservice.resources;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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

import com.fidelity.magic.api.constants.DuplicateChecker;
import com.fidelity.magic.api.constants.EventSeverity;
import com.fidelity.magic.webservice.api.itsm.Incident;
import com.fidelity.magic.webservice.api.itsm.IncidentArray;
import com.fidelity.magic.webservice.api.itsm.IncidentCache;
import com.fidelity.magic.webservice.util.Constants;
import com.fidelity.magic.webservice.util.UIDGenerator;

/**
 * jMagic Incident Webservice, allows you to send incident object
 * 
 * @author Jackie Shi
 * @version 1.0
 */
// Sets the path to base URL + /event
@Path("/incident")
public class IncidentResource {

	static Logger logger = Logger.getLogger(Constants.log4jPrefix
			+ IncidentResource.class.getName());

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
	@Produces({ MediaType.APPLICATION_JSON })
	public IncidentArray getIncident(@QueryParam("number") String number) {
		IncidentCache cache = IncidentCache.getInstance();
		IncidentArray array = new IncidentArray();
		List<Incident> list = new ArrayList<Incident>();
	
	
		String [] allNumbers = number.split(" ");
		for (int i = 0; i < allNumbers.length; i++) {
			String id = allNumbers[i];
			Incident in = cache.getIncident(id);
			
			if (in == null)
				throw new RuntimeException("Get: event not found");

			list.add(in);
		}
		
		array.setIncidentList(list);
		return array;
	}
	
	/** GET method: get detailed Incident information for a specific Incident */
	@Path("/all")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public IncidentArray getIncident() {
		IncidentCache cache = IncidentCache.getInstance();
		IncidentArray array = new IncidentArray();
		List<Incident> list = new ArrayList<Incident>();

		Map<String, Incident> map = cache.getAllIncident();
		Iterator<Incident> it = map.values().iterator();
		while (it.hasNext())
		{
			list.add(it.next());
		}
		
		array.setIncidentList(list);
		return array;
	}

	/** PUT method: new incident */
	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String newIncident(IncidentArray incidentArray) {

		//IncidentArray incidentArray = inputList.getValue();
		if (incidentArray != null) 
		{
			List<Incident> list = incidentArray.getIncidentList();
			if (list != null && list.size() >0) 
			{
				StringBuilder sb = new StringBuilder("jMagic received new incidents: "+list.size()+": ");
				IncidentCache cache = IncidentCache.getInstance();
				for (Incident incident : list) 
				{
					// String uuid = UIDGenerator.getUID(type);
					if (incident != null) 
					{
						sb.append(incident.getNumber() +" ");						
						cache.addToCache(incident);

					} else
						throw new RuntimeException("not a valid incident input");

				}
				logger.info(sb.toString());
				return sb.toString(); 
			}
		}

		return "Not a valid input";
	}

	/** POST method: update incident */
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String updateIncident(IncidentArray incidentArray) {
		
		//IncidentArray incidentArray = inputList.getValue();
		if (incidentArray != null) 
		{
			List<Incident> list = incidentArray.getIncidentList();
			if (list != null && list.size() >0) 
			{
				StringBuilder sb = new StringBuilder("jMagic received updated incidents: "+list.size()+": ");
				IncidentCache cache = IncidentCache.getInstance();
				for (Incident incident : list) 
				{
					// String uuid = UIDGenerator.getUID(type);
					if (incident != null) 
					{
						sb.append(incident.getNumber() +" ");						
						cache.updateIncident(incident);

					} else
						throw new RuntimeException("not a valid incident input");

				}
				logger.info(sb.toString());
				return sb.toString(); 
			}
		}

		return "Not a valid input";
		
	}

	@DELETE
	public void deleteEvent() {

		throw new RuntimeException("Delete: event  not found");
	}

}
