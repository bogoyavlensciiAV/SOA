package jpchs.isuApp.controller;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jpchs.isuApp.ejb.api.IsuService;

@Path("/group")
public class IsuController {

    @EJB
    private IsuService service;

    @POST
    @Path("/{from-group}/move/{to-group}")
    public Response moveStudents(
            @PathParam("from-group") long fromId,
            @PathParam("to-group") long toId) {

        try {
            service.moveStudents(fromId, toId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    @POST
    @Path("/{group-id}/change-edu-form/{new-form}")
    public Response changeEduForm(
            @PathParam("group-id") long groupId,
            @PathParam("new-form") String newForm) {

        service.changeEduForm(groupId, newForm);
        return Response.noContent().build();
    }
}