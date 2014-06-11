package com.fidelity.magic.webservice.api.itsm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This wrapper class is to get around Jersey REST limitation 
 * 
 * Jersey is unable to map a list of JSON entities 
 * when implementing a method which consumes a list of JSON-ised entities
 * for example:
 * myMethod(List<JAXBElement<Incident>> list)
 * 
 * @author A322448
 *
 */
@XmlRootElement
public class IncidentArray {
	
	//contains array of incidents
	private List<Incident> incidentList;
	
	public IncidentArray()
	{
		//this.incidentList = new ArrayList<Incident>();
	}
	public List<Incident> getIncidentList() {
		return incidentList;
	}
	public void setIncidentList(List<Incident> incidentList) {
		this.incidentList = incidentList;
	}
	

}
