package com.magic.webservice.api;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 
 * @author Jackie Shi
 * @version 1.0
 * @Since 10/10/2013
 */

@XmlRootElement
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Event {
	
	private String objectName;
	private String eventClass;
	private String eventText;
	private String severity;
	private String host;
	private String category;
	private String opdoc;
	
	private Map<String, String> nameValuePairs;
	
	public Event(){}
	
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getEventClass() {
		return eventClass;
	}
	public void setEventClass(String eventClass) {
		this.eventClass = eventClass;
	}
	public String getEventText() {
		return eventText;
	}
	public void setEventText(String eventText) {
		this.eventText = eventText;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
		
	public Map<String, String> getNameValuePairs() {
		return nameValuePairs;
	}
	public void setNameValuePairs(Map<String, String> nameValuePairs) {
		this.nameValuePairs = nameValuePairs;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOpdoc() {
		return opdoc;
	}

	public void setOpdoc(String opdoc) {
		this.opdoc = opdoc;
	}
	
    public String toString()
    {
    	StringBuffer  sb = new StringBuffer();
    	sb.append("objectName"+objectName);
    	sb.append(",eventClass"+eventClass);
    	sb.append(",host"+host);
    	sb.append(",eventText"+eventText);
    	return sb.toString();
    }
}
