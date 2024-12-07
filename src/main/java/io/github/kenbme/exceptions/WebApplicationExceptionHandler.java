package io.github.kenbme.exceptions;

import java.util.Map;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionHandler implements ExceptionMapper<WebApplicationException> {

  @Override
  public Response toResponse(WebApplicationException exception) {
    var response = exception.getResponse();
    return Response.status(response.getStatus())
        .entity(Map.of("error", exception.getMessage()))
        .build();
  }

}
