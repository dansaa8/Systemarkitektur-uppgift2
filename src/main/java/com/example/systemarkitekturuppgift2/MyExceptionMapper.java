package com.example.systemarkitekturuppgift2;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class MyExceptionMapper implements ExceptionMapper<MyException> {
    private static final Logger logger = LoggerFactory.getLogger(MyExceptionMapper.class);

    @Override
    public Response toResponse(MyException exception) {
        logger.error(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}
