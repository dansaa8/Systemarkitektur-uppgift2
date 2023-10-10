package com.example.systemarkitekturuppgift2.resource;

import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import com.example.systemarkitekturuppgift2.service.WarehouseService;
import com.example.systemarkitekturuppgift2.service.WarehouseTestService;
import static com.example.systemarkitekturuppgift2.util.EndpointValidator.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.print.attribute.standard.Media;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Path("products")
public class ProductResource {
    private WarehouseService wh;

    private final ObjectMapper objectMapper = new ObjectMapper();

//    public ProductResource() {}

    @Inject
    public ProductResource() {
        this.wh = WarehouseTestService.create();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public Response createProduct(String productJson) {
        try {
            // Deserialize the JSON data into a ProductRecord object
            ProductRecord product = objectMapper.readValue(productJson, ProductRecord.class);

            // Your POST request handling logic here
            wh.addProduct(product);
            // You can access the 'product' object, which is a ProductRecord

            // Serialize the response data back to JSON
            String responseJson = "{\"message\": \"Product created successfully\"}";

            return Response.status(Response.Status.CREATED)
                    .entity(responseJson)
                    .build();
        } catch (Exception e) {
            // Handle any exceptions that may occur during JSON deserialization
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid JSON data: " + e.getMessage())
                    .build();
        }
    }


    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAll() {
        return wh.getAllProducts();
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@QueryParam("category") Category category) {
        System.out.println(category);
        if (category != null && isValidCategory(category))
            return Response.ok(wh.getProductsByCategory(category)).build();
        else {
            String errorMessage = "Invalid category provided.";
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("id") int id) {
        Optional product = wh.getProduct(id);
        if (product.isPresent())
            return Response.ok(product.get()).build();
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/createdAfter")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCreatedAfter(@QueryParam("date")String dateParam) {
        LocalDate date = parseDate(dateParam);
        if (date == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();

        return Response.ok().entity(wh.getProductsCreatedAfterDate(date)).build();
    }

    @GET
    @Path("/modifiedAfter")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModifiedAfter(@QueryParam("date")String dateParam) {
        LocalDate date = parseDate(dateParam);
        if (date == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();

        return Response.ok().entity(wh.getProductsModifiedAfterDate(date)).build();
    }

    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<String>> getCategories() {
        return wh.getExistingCategories();
    }

    @GET
    @Path("/firstLetters")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Long> getFirstLetters() {
        return wh.getFirstLetters();
    }

    @GET
    @Path("/new/maxRating")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductRecord> getMaxRated() {
        return wh.getProductsWithMaxRating(LocalDate.now());
    }
}

