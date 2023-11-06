package com.example.warehousewebserver.resource;

import com.example.warehousewebserver.Log;
import com.example.warehousewebserver.entities.Category;
import com.example.warehousewebserver.entities.ProductRecord;
import com.example.warehousewebserver.exception.ProductConflictException;
import com.example.warehousewebserver.exception.ProductNotFoundException;
import com.example.warehousewebserver.service.WarehouseService;
import com.example.warehousewebserver.util.CustomResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Path("products")
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
    public Response uploadProduct(@Valid ProductRecord p) {
        boolean isAdded = wh.addProduct(p);
        if (!isAdded) {
            throw new ProductConflictException("Product with id and/or name already exists");
        }
        return CustomResponse.productAdded(p);
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
            return CustomResponse.productFound(product);

        throw new ProductNotFoundException("", id);
    }
}
