package com.magic.webservice.api;

import javax.ws.rs.WebApplicationException;

import com.magic.webservice.authentication.jMagicUnauthorizedException;
import com.magic.webservice.resources.LoginResource;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;


/**
 * 
 * @author A322448
 *
 */
public class AuthFilter implements ContainerRequestFilter{
    /**
     * Apply the filter to all resources except for Login: check input request, validate if user has token header 
     * @param containerRequest The request from Tomcat server
     */
    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) throws WebApplicationException {
        //GET, POST, PUT, DELETE, ...
        String method = containerRequest.getMethod();
        // myresource/get/56bCA for example
        String path = containerRequest.getPath(true);
 
        //We do allow wadl to be retrieve, and let login call pass
        if(path.equals("login") || path.equals("documentation") //bypass login for swagger link
        		|| (method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))))
        {	
            return containerRequest;
        }
 
        //Get the token passed in HTTP headers parameters
        String token = containerRequest.getHeaderValue(LoginResource.HEADER_TOKEN);
 
        //If the user does not have a token
        if(token == null || token.trim().length()  ==0 ){
        	throw new jMagicUnauthorizedException(LoginResource.UNAUTH_ERROR_MSG);
        }
 
        //check for valid token
        CachedObjects cache = CachedObjects.getInstance();
        if (!cache.hasToken(token))
        	throw new jMagicUnauthorizedException(LoginResource.TOKEN_ERROR_MSG);
        	
        //since Jersey doens't print request body on Tomcat, let us log the body
        
        //TODO : HERE YOU SHOULD ADD PARAMETER TO REQUEST, TO REMEMBER USER ON YOUR REST SERVICE...
 
        return containerRequest;
    }
}