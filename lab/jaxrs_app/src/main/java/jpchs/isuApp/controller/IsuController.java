package jpchs.isuApp.controller;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jpchs.isuApp.client.StudyGroupClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Map;

@Path("/group")
public class IsuController {

    @Inject
    @RestClient
    StudyGroupClient client;

    @POST
    @Path("/{from-group}/move/{to-group}")
    public Response moveStudents(
            @PathParam("from-group") long fromId,
            @PathParam("to-group") long toId) {

        if (fromId == toId) {
            return Response.status(Response.Status.CONFLICT)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        JsonObject from = client.getById(fromId);
        JsonObject to = client.getById(toId);

        long moved = from.getJsonNumber("students-count").longValue();
        long targetStudents = to.getJsonNumber("students-count").longValue();

        JsonObject patchedTo = Json.createObjectBuilder(to)
                .add("students-count", targetStudents + moved)
                .build();

        JsonObject patchedFrom = Json.createObjectBuilder(from)
                .add("students-count", 0L)
                .build();

        client.update(toId, patchedTo);
        client.update(fromId, patchedFrom);

        return Response.noContent().build();
    }

    @POST
    @Path("/{group-id}/change-edu-form/{new-form}")
    public Response changeEduForm(
            @PathParam("group-id") long groupId,
            @PathParam("new-form") String newForm) {

        JsonObject group = client.getById(groupId);

        JsonObject patchedGroup = Json.createObjectBuilder(group)
                .add("form-of-education", newForm)
                .build();

        client.update(groupId, patchedGroup);

        return Response.noContent().build();
    }
}