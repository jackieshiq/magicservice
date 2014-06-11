package com.fidelity.magic.webservice.api;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
/**
 * used to store name value pair
 * @author A322448
 *
 */
public class MapElements
{
  @XmlElement
  public String  key;
//  @XmlElement public Integer value;
  @XmlElement 
  public String  value;
  private MapElements() {} //Required by JAXB

  public MapElements(String key, String value)
  {
    this.key   = key;
    this.value = value;
  }
}