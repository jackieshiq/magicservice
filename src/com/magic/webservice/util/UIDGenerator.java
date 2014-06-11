package com.magic.webservice.util;

import java.text.SimpleDateFormat;

public class UIDGenerator {
	
	  private static int uid = 0;
	  
	  private static SimpleDateFormat formatterLong =
	      new SimpleDateFormat("yyMMddHHmmssSSS");
	  
	  public static synchronized String getUID(String prefix)
	  {
	    
	    if(++uid >= 1000) uid = 0; 
	    String x = prefix +"-";
	    if(x.length() > 18)
	    x = x.substring(0,18);
	    x += formatterLong.format(new java.util.Date()) + UIDGenerator.normalizeInt(uid); //make sure this is 009 format 
	    return x;
	  }
	  
	    /* return 009 for integer 9 */
	    public static String normalizeInt(int value)
	    {
	        int length =3;
	        return normalizeInt(value, length);
	    }
	    
	    //length must be >=1
	    public static String normalizeInt(int value, int length)
	    {
	       String pattern = "%0"+length+"d"; //padding with left 0
	       return String.format(pattern, value);
	    }

}
