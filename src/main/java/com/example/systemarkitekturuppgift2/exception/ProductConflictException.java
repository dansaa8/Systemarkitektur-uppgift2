package com.example.systemarkitekturuppgift2.exception;

import jakarta.ws.rs.WebApplicationException;

public class ProductConflictException extends WebApplicationException {
    public ProductConflictException() {super();}
    public ProductConflictException(String message) {super(message); }
}
