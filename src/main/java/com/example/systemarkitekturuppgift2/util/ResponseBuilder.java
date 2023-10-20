package com.example.systemarkitekturuppgift2.util;

import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public final class ResponseBuilder {
    private ResponseBuilder() {}

    public static Response ProductAdded(ProductRecord p) {
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder()
                .add("message", "Product added successfully")
                .add("product", p.toString());

        return Response.status(Response.Status.CREATED)
                .entity(responseBuilder.build().toString()) // Convert JsonObject to JSON string
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
