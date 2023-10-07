package com.example.systemarkitekturuppgift2.resource;

import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import com.example.systemarkitekturuppgift2.service.WarehouseService;
import com.example.systemarkitekturuppgift2.service.WarehouseTestService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("products")
public class ProductResource {
    private WarehouseService wh;
    public ProductResource() {}

    @Inject
    public ProductResource(WarehouseTestService wh) {
        this.wh = wh;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public String addProduct(ProductRecord p) {
        return p.toString();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List query() {
        return wh.getAllProducts();
    }
}

