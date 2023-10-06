package com.example.systemarkitekturuppgift2;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;

@ApplicationScoped
public class HelloGreetingService implements GreetingService{
    @Override
    public String greeting() {
        return "Hello World";
    }

    // kan göra metoder som returnerar service
    // annotering produces. när weld behöver få tag i greetingservice
    // hittar metod som är annoterad Produces
    // kan lära weld hur den ska skapa obj av typen.
    // kan returnera oliak typer o hantera konstruktorer.
    // om man har factorymetod även.
    @Produces
    public static GreetingService create() {
        return new HelloGreetingService();
    }
}
