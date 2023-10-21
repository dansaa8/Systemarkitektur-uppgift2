package com.example.systemarkitekturuppgift2.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .type("application/json")
                .build();
    }

    private Map<String, List<String>> prepareMessage(ConstraintViolationException exception) {
        Map<String, List<String>> result = new HashMap<>();
        List<String> violations = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        result.put("ConstraintViolations", violations);
        return result;
    }
}