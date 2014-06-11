package com.magic.webservice.api;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.magic.webservice.authentication.MagicAuthentication;
import com.magic.webservice.resources.LoginResource;
import com.magic.webservice.util.Constants;
import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.api.container.filter.LoggingFilter;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

/**
 * Extends  com.sun.jersey.api.container.filter.LoggingFilter
 * Which logs all header info including sensitive info such as password
 * 
 * this logging filter can be registered with both request and response containers
 *  <init-param>
	  <param-name>com.sun.jersey.spi.container.ContainerRequestFilters</param-name>
	  <param-value></param-value>
	</init-param>
 * @author A322448
 *
 */
public class MagicLoggingFilter extends LoggingFilter{

	@Override
	public ContainerRequest filter(ContainerRequest request) {
		
		//remove password from header
		List<String> values = request.getRequestHeaders().remove(LoginResource.HEADER_PASSWORD);
		//let super class print
	    super.filter(request);	    
	    //add back the password
	    if (values !=null && values.size() > 0)
	    {
	    	String pw = (String)values.get(0);
	    	request.getRequestHeaders().add(LoginResource.HEADER_PASSWORD, pw);
	    }
	    
	    return request;
	}

	@Override
	public ContainerResponse filter(ContainerRequest request,
			ContainerResponse response) {
		
		return super.filter(request, response);
	}
	
	
	
	
	
}
