package com.example.systemarkitekturuppgift2.resource;

import com.example.systemarkitekturuppgift2.Log;
import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import com.example.systemarkitekturuppgift2.service.WarehouseBusinessService;
import com.example.systemarkitekturuppgift2.service.WarehouseService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.systemarkitekturuppgift2.util.EndpointValidator.isValidCategory;
import static com.example.systemarkitekturuppgift2.util.EndpointValidator.parseDate;


@Path("products")
// Sätt till application JSON.
@Log
public class ProductResource {
    private WarehouseService wh;

    private static final Logger logger = LoggerFactory.getLogger(ProductResource.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

//    public ProductResource() {}

    @Inject
    public ProductResource() {
        this.wh = WarehouseBusinessService.create();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public void uploadProduct(@Valid ProductRecord p) {
        logger.info("Product uploaded " + p);
    }


    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAll(@Context UriInfo uri) {
//        logger.info("Connection to " + uri.getAbsolutePath());
//        logger.warn("/getAll called");
//        logger.error("/getAll called");
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
            logger.error("/id product with id doesn't exist");
            logger.info("/products/" + id + " called");
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

