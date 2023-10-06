package com.example.systemarkitekturuppgift2;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.List;

@Path("")
public class ProductResource {

    private GreetingService greetingService;

    public ProductResource(){}; //måste ha, även om vi inte ska använda.

    //constructor injection.
    // i testmiljö kan är constructor injection bättre.
    @Inject
    public ProductResource(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GET
    @Produces("text/plain")
    @Path("/hello")
    public String hello() {
        return greetingService.greeting();
    }

    @Path("/products")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> product() {
        return List.of( new Product(1, "Car", Category.VEHICLES,
                6, LocalDate.now(), LocalDate.now()));
    }

    ///exempel: warehouse/query?category=vehicles
    @GET
    @Path("/query")
    @Produces(MediaType.TEXT_PLAIN)
    public String query(@QueryParam("category") String input) {
        return input;
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductById(@PathParam("id") int id) {
        return new Product(id, "Car", Category.VEHICLES,
                6, LocalDate.now(), LocalDate.now());
    }

    @GET
    @Path("/products/{id}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductById(@PathParam("id") int id, @PathParam("name") String name) {
        return new Product(id, name, Category.VEHICLES,
                6, LocalDate.now(), LocalDate.now());
    }

    @GET
    @Path("/customresponse")
    @Produces(MediaType.TEXT_PLAIN)
    public Response customResponse() {
        return Response.ok().status(201).entity("HAJHAJHAJ").header("MyHeader", "Testing").build();
    }
}

