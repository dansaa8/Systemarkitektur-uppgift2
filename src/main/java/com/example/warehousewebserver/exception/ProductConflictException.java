package com.example.warehousewebserver.exception;

import jakarta.ws.rs.WebApplicationException;

public class ProductConflictException extends WebApplicationException {
    public ProductConflictException() {super();}
    public ProductConflictException(String message) {super(message); }
}
