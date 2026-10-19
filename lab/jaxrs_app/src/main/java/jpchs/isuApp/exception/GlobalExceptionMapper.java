package jpchs.isuApp.exception;

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
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable e) {
        // Spring-сервис недоступен: соединение, таймаут, TLS
        if (e instanceof ProcessingException) {
            LOG.log(Level.WARNING, "Study groups service unavailable", e);
            return error(Response.Status.SERVICE_UNAVAILABLE,
                    Errors.SERVICE_UNAVAILABLE,
                    "Study groups service is unavailable");
        }

        // Spring ответил своей ошибкой (404, 422 ...) - пробрасываем статус и тело как есть
        if (e instanceof WebApplicationException wae) {
            Response upstream = wae.getResponse();
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
                // упадём на стандартное тело ниже
            }
            return error(Response.Status.fromStatusCode(upstream.getStatus()),
                    Errors.UPSTREAM_ERROR,
                    "Upstream error");
        }

        LOG.log(Level.SEVERE, "Unhandled exception", e);
        return error(Response.Status.INTERNAL_SERVER_ERROR,
                Errors.UPSTREAM_ERROR,
                "Internal server error");
    }

    private Response error(Response.StatusType status, Errors code, String message) {
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorDTO(List.of(new ErrorItem(code, message))))
                .build();
    }
}