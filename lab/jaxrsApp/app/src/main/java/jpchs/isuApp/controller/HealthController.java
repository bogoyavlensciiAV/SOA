package jpchs.isuApp.controller;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jpchs.isuApp.ejb.api.IsuService;

@Path("/health")
public class HealthController {
    @GET
    public Response healthCheck() {
        return Response.ok("OK").build();
    }
}