package com.example.systemarkitekturuppgift2.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ResponseData {
    private String message;

    public ResponseData() {
        // Default constructor
    }

    public ResponseData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
