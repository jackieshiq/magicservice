package com.magic.webservice.api.itsm;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Journal {
	
	public Journal()
	{}
	
	private String created;//:"2014-02-07 19:21:11",
	private String element; //:"work_notes",
	private String value; //":"We are experiencing many problems"
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
    	
	

}
