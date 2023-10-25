package com.example.warehousewebserver.exception;

import jakarta.ws.rs.WebApplicationException;

public class ProductNotFoundException extends WebApplicationException {
    public ProductNotFoundException() {super();}
    public ProductNotFoundException(String message, int id) {
        super(message = "Product with id " + id + " could not be found");
    }
}
