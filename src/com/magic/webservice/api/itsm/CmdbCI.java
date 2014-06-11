package com.magic.webservice.api.itsm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cmdb_ci")
public class CmdbCI {

	 //<cmdb_ci display_value="MailServerUS">b0c4030ac0a800090152e7a4564ca36c</cmdb_ci>
	    
	@XmlAttribute
	protected String display_value;
   
    @XmlValue
    protected String description;

}
