package com.example.systemarkitekturuppgift2;

import jakarta.ws.rs.WebApplicationException;

public class MyException extends WebApplicationException {
    public MyException() {super();}
    public MyException(String message) {super("MyException error " + message); }
}
