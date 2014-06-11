package com.magic.webservice.util;

import java.io.*;
import java.net.*;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger; //log4j
import org.apache.log4j.PropertyConfigurator;

import com.magic.api.constants.DuplicateChecker;
import com.magic.api.constants.EventSeverity;
import com.sun.net.ssl.SSLContext;
import com.sun.net.ssl.TrustManager;
import com.sun.net.ssl.X509TrustManager;
/**
 * @author A322448
 *
 * Send http paylod to any end point
 * using apache common http client
 * 
 * */
public class ApiPayLoad 
{
	private static Logger logger = Logger.getLogger(Constants.log4jPrefix+ApiPayLoad.class.getName());


	public static String sendPayLoad( String remoteUrl, String inputString , String contentType, String method ) 
	{
			// verify attributes
			if (remoteUrl == null || remoteUrl.trim().length() == 0) {
				return "Missing remote URL";
			}
			if (inputString == null || inputString.trim().length() == 0) {
				return "Missing payload";
			}
	
			if (contentType == null || contentType.trim().length() == 0) {
				// default to xml
				contentType = "application/xml";
			}
			if (method == null || method.trim().length() == 0) {
				// default to POST
				method = "POST";
			}
			
			// this is to stuff the final results in
   	        StringBuffer result = new StringBuffer();
   	        HttpClient httpClient = new HttpClient();
			try{
	
				StringRequestEntity requestEntity = new StringRequestEntity(
						inputString, contentType, "UTF-8");
		
				if ("POST".equalsIgnoreCase(method))
				{
					PostMethod postMethod = new PostMethod(remoteUrl);
					postMethod.setRequestEntity(requestEntity);		
					int statusCode = httpClient.executeMethod(postMethod);
					result.append((new StringBuilder("statuscode=")).append(statusCode).toString());
					result.append(", ");
					result.append((new StringBuilder("remote url=")).append(remoteUrl).toString());
					result.append(", ");
		            result.append(postMethod.getResponseBodyAsString());
					
				}else if ("PUT".equalsIgnoreCase(method))
				{
					PutMethod putMethod = new PutMethod(remoteUrl);
					putMethod.setRequestEntity(requestEntity);
			
					int statusCode = httpClient.executeMethod(putMethod);	
					result.append((new StringBuilder("statuscode=")).append(statusCode).toString());
					result.append(", ");
					result.append((new StringBuilder("remote url=")).append(remoteUrl).toString());
					result.append(", ");
		            result.append(putMethod.getResponseBodyAsString());
				}
				
				
			} catch (MalformedURLException e) {
   	            result = new StringBuffer("Error: Problems with the URL : XmlSender.send()");
   	         logger.error(e);
   	        } catch (IOException e) {
   	            result = new StringBuffer( "Error: Problems with IO :" +e.getMessage() );
   	         logger.error(e);
   	        }catch(Exception e1)
			{
   	        	result = new StringBuffer( "Error: Problems with "+e1.getMessage()+" : sendPayLoad()" );
   	        	logger.error(e1);
			}
   	 
   	        return result.toString();
	}
	

	
}
