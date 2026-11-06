package jpchs.isuApp.exception;

import jakarta.ejb.EJBException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jpchs.isuApp.dto.ErrorDTO;
import jpchs.isuApp.dto.ErrorItem;
import jpchs.isuApp.dto.Errors;

import java.util.List;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable e) {
        e.printStackTrace();
        while (e instanceof EJBException && e.getCause() != null) {
            e = e.getCause();
        }

        if (e instanceof ProcessingException) {
            return error(Errors.SERVICE_UNAVAILABLE,
                    "Study groups service is unavailable");
        }

        if (e instanceof WebApplicationException wae) {
            Response upstream = wae.getResponse();
            System.out.println("Status: " + upstream.getStatus());
            System.out.println("Reason: " + upstream.getStatusInfo().getReasonPhrase());
            System.out.println("Has entity: " + upstream.hasEntity());

            try {
                if (upstream.hasEntity()) {
                    String json = upstream.readEntity(String.class);
                    if (json != null && !json.isBlank()) {
                        return Response.status(upstream.getStatus())
                                .type(MediaType.APPLICATION_JSON)
                                .entity(json)
                                .build();
                    }
                }
            } catch (Exception ignored) {
                System.out.println("Status: " + upstream.getStatus());
                System.out.println("Allow: " + upstream.getHeaderString("Allow"));
                System.out.println("Location: " + upstream.getHeaderString("Location"));
            }
            return error(Errors.UPSTREAM_ERROR, "Upstream error");
        }

        return error(Errors.UPSTREAM_ERROR, "Internal server error");
    }

    private Response error(Errors code, String message) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorDTO(List.of(new ErrorItem(code, message))))
                .build();
    }
}