package com.fidelity.magic.webservice.authentication;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;


public class jMagicUnauthorizedException extends WebApplicationException {

    /**
      * Create a HTTP 401 (Unauthorized) exception.
     */
     public jMagicUnauthorizedException() {
         super(Response.status(Status.UNAUTHORIZED).build());
     }

     /**
      * Create a custom message for HTTP 401 exception.
      * @param message the String that is the entity of the 404 response.
      */
     public jMagicUnauthorizedException(String message) {
         super(Response.status(Status.UNAUTHORIZED).entity(message).type("text/plain").build());
     }

}

