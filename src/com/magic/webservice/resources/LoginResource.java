package com.fidelity.magic.webservice.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.fidelity.magic.api.util.SecurityToken;
import com.fidelity.magic.webservice.authentication.MagicAuthentication;
import com.fidelity.magic.webservice.authentication.jMagicUnauthorizedException;
import com.fidelity.magic.webservice.util.Constants;
import com.wordnik.swagger.annotations.*;


/**
 * jMagic Login Webservice,
 * return an access token used for subsequent webservice calls
 * 
 * @author Jackie Shi
 * 05/15/2014
 */


@Api(value = "/login", description = "Login to obtain access token")
@Path("/login")
public class LoginResource {
	
	//some static values
	public static final String HEADER_USERNAME = "username";
	public static final String HEADER_PASSWORD = "password";
	public static final String HEADER_USERMODE = "usermode";
	public static final String HEADER_TOKEN = "access-token";
	
	public static final String UNAUTH_ERROR_MSG = "Please provide a valid username, password, and jMagic user mode";
	public static final String TOKEN_ERROR_MSG = "Please provide a valid token. To obatain a new token, please log in again";
	public static final String NOT_AUTHORIZED_MSG = "Your account is not authorized to access jMagic API";
	
	static Logger logger = Logger.getLogger(Constants.log4jPrefix+LoginResource.class.getName());
	
	/**
	 * For user to get a valid token via get method 
	 * @param header
	 * @return
	 */
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	//@ApiOperation(value = "get a valid access token", notes = "youtoken will expire in 24 hours", response = Response.class)
	public Response getAccessToken(@Context HttpHeaders header){
		
		try{
			Response res;
		   
			MultivaluedMap<String, String> map = header.getRequestHeaders();
			if (map !=null && map.size() >0)
			{
				String username = map.getFirst(HEADER_USERNAME);
				String password = map.getFirst(HEADER_PASSWORD);
				String usermode = map.getFirst(HEADER_USERMODE);
								
				SecurityToken objToken = MagicAuthentication.getSecurityToken(username, password, usermode, true); //encrypt password please
				if (objToken != null )
				{
					String token = objToken.getToken();					
					if (token != null && token.trim().length() > 0)
					{
						res= Response.ok().header(HEADER_TOKEN, token).build();
						logger.info("return token " +token);
						return res;
					}
				}
				
			}
		}catch (Exception e) {
			throw new jMagicUnauthorizedException(e.getMessage());
		}
		throw new jMagicUnauthorizedException(UNAUTH_ERROR_MSG);
	}
	
	
	
	/**
	 * all browser test
	 * @param username
	 * @param password
	 * @param usermode
	 * @return
	 */
	/*
	  @GET
	  @Produces("application/json")
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public Response getAccessTokenFromForm
	  		(@FormParam(HEADER_USERNAME) String username,
	  				@FormParam(HEADER_PASSWORD) String password,
	  				@FormParam(HEADER_USERMODE) String usermode)
	  {
		try{
			Response res;
			SecurityToken objToken = MagicAuthentication.getSecurityToken(username, password, usermode, true); //encrypt password please
			if (objToken != null )
			{
				String token = objToken.getToken();					
				if (token != null && token.trim().length() > 0)
				{
					res= Response.ok().header(HEADER_TOKEN, token).build();
					logger.info("return token " +token);
					return res;
				}
			}

		}catch (Exception e) {
			throw new jMagicUnauthorizedException(e.getMessage());
		}
			throw new jMagicUnauthorizedException(UNAUTH_ERROR_MSG);
	  }
*/

}
