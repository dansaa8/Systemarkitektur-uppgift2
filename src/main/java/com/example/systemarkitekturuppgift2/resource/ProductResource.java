package com.example.systemarkitekturuppgift2.resource;

import com.example.systemarkitekturuppgift2.ConstraintViolationExceptionMapper;
import com.example.systemarkitekturuppgift2.Log;
import com.example.systemarkitekturuppgift2.MyException;
import com.example.systemarkitekturuppgift2.constraint.ValidCategory;
import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import com.example.systemarkitekturuppgift2.service.WarehouseBusinessService;
import com.example.systemarkitekturuppgift2.service.WarehouseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import jakarta.validation.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Path("products")
// Sätt till application JSON.
@Log
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    private WarehouseService wh;

    private static final Logger logger = LoggerFactory.getLogger(ProductResource.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductResource() {}

    @Inject
    public ProductResource(WarehouseService warehouseService) {
        this.wh = warehouseService;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public ResponseData uploadProduct(@Valid ProductRecord p) {
        boolean isAdded = wh.addProduct(p);
        if (!isAdded) {
            return new ResponseData("Product with id and/or name already exists");
        }
        return new ResponseData("Product added successfully");
    }

    @GET
    @Path("")
    public List getAll(@Context UriInfo uri) {
        return wh.getAllProducts();
    }

    @GET
    @Path("/query")
    public Response getCategory(@QueryParam("category") Category category) {
            return Response.ok(wh.getProductsByCategory(category)).build();
    }

    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") int id) {
        Optional product = wh.getProduct(id);
        if (product.isPresent())
            return Response.ok(product.get()).build();

        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ResponseData("Product not found"))
                .build();
    }
}

