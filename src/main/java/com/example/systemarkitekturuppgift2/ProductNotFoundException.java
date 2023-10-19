package com.example.systemarkitekturuppgift2;

import jakarta.ws.rs.WebApplicationException;

public class ProductNotFoundException extends WebApplicationException {
    public ProductNotFoundException() {super();}
    public ProductNotFoundException(String message) {super(message); }
}
