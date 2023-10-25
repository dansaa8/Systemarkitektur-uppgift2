package com.example.warehousewebserver.util;

import com.example.warehousewebserver.entities.ProductRecord;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.Optional;

public final class CustomResponse {
    private CustomResponse() {}

    public static Response productAdded(ProductRecord p) {
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder()
                .add("message", "Product added successfully")
                .add("product", p.toString());

        return Response.status(Response.Status.CREATED)
                .entity(responseBuilder.build().toString()) // Convert JsonObject to JSON string
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    public static Response productFound(Optional p) {
        return Response.status(Response.Status.OK)
                .entity(p.get())
                .build();
    }
}
