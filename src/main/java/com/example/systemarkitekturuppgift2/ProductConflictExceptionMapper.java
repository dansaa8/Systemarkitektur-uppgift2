package com.example.systemarkitekturuppgift2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Provider
public class ProductConflictExceptionMapper implements ExceptionMapper<ProductConflictException> {
    private static final Logger logger = LoggerFactory.getLogger(ProductConflictException.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Response toResponse(ProductConflictException exception) {
        logger.error(exception.getMessage());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("ProductConflictException error", exception.getMessage());

        try {
            String json = objectMapper.writeValueAsString(errorResponse);

        return Response.status(Response.Status.CONFLICT)
                .entity(json)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
        } catch (JsonProcessingException e) {
            logger.error("Error while creating JSON response", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
