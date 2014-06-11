package com.fidelity.magic.webservice.api.itsm;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="xml")
public class XML {
	private Incident incident;

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
}
