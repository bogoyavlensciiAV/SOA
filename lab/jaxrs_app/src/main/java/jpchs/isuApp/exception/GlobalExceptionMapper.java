package jpchs.isuApp.exception;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof WebApplicationException webException) {
            Response response = webException.getResponse();

            return Response.status(response.getStatus())
                    .type(MediaType.APPLICATION_JSON)
                    .entity(response.getEntity())
                    .build();
        }

        JsonObject body = Json.createObjectBuilder()
                .add("status", 500)
                .add("title", "Internal Server Error")
                .add("detail", "Внутренняя ошибка сервера")
                .build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}