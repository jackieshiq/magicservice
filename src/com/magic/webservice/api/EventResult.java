package com.magic.webservice.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EventResult {

	
	private String uuid;
	private String message;

	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
