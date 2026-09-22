package jpchs.isuApp.client;

import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Map;

@RegisterRestClient(configKey = "study-group-api", baseUri = "http://localhost:40834/study-groups-app")
@Path("/study-groups")
public interface StudyGroupClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject getById(@PathParam("id") long id);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject update(
            @PathParam("id") long id,
            Map<String, Object> body
    );
}