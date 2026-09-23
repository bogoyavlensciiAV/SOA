package jpchs.isuApp.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    private static final String ALLOWED_ORIGIN = "http://localhost:8080";

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) {

        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Origin", ALLOWED_ORIGIN);
        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Headers", "*");
        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().putSingle(
                "Access-Control-Max-Age", "3600");
    }
}