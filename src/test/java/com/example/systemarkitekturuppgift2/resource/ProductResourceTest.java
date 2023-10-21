package com.example.systemarkitekturuppgift2.resource;

import com.example.systemarkitekturuppgift2.JacksonConfig;
import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import com.example.systemarkitekturuppgift2.exception.*;
import com.example.systemarkitekturuppgift2.service.WarehouseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {

    @Mock
    WarehouseService warehouseService;

    ObjectMapper objectMapper = new ObjectMapper();
    Dispatcher dispatcher; //simulerad webbserver

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        var productResource = new ProductResource(warehouseService);
        dispatcher.getRegistry().addSingletonResource(productResource);
        // Create your custom ExceptionMapper
        ExceptionMapper <ConstraintViolationException> mapper = new ConstraintViolationExceptionMapper();
        ExceptionMapper <ProductNotFoundException> pNotFoundMapper = new ProductNotFoundExceptionMapper();
        ExceptionMapper <ProductConflictException> pConflictMapper = new ProductConflictExceptionMapper();
        // Register your custom ExceptionMapper
        dispatcher.getProviderFactory().registerProviderInstance(mapper);
        dispatcher.getProviderFactory().registerProviderInstance(pNotFoundMapper);
        dispatcher.getProviderFactory().registerProviderInstance(pConflictMapper);

        dispatcher.getProviderFactory().registerProvider(JacksonConfig.class);
        objectMapper.registerModule(new JavaTimeModule());
    }

    private List<ProductRecord> mockedProductList() {
        return List.of(
                new ProductRecord(1, "P1", Category.COMPUTERS, 1,
                        LocalDate.of(2021, 1, 1),
                        LocalDate.of(2021, 1, 1))
        );
    }

    private ProductRecord getSingleProduct() {
        return new ProductRecord(1, "P1", Category.COMPUTERS, 1,
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 1));
    }



    @Test
    public void productsReturnsAllProductsWithStatus200() throws Exception {

        Mockito.when(warehouseService.getAllProducts()).thenReturn(mockedProductList());
        MockHttpRequest request = MockHttpRequest.get("/products");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());

        JSONAssert.assertEquals("[{" +
                        "\"id\":1," +
                        "\"name\":\"P1\"," +
                        "\"category\":" +
                        "\"COMPUTERS\"," +
                        "\"rating\":1," +
                        "\"createdAt\":\"2021-01-01\"," +
                        "\"lastModified\":\"2021-01-01\"}]",
        response.getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    public void addProductReturnsOkMessageWithStatus201() throws Exception {
        ProductRecord p = getSingleProduct();
        Mockito.when(warehouseService.addProduct(p)).thenReturn(true);
        MockHttpRequest req = MockHttpRequest.post("/products");
        req.contentType("application/json");

        String jsonPayload = objectMapper.writeValueAsString(p);
        req.content(jsonPayload.getBytes("UTF-8"));

        MockHttpResponse res = new MockHttpResponse();

        dispatcher.invoke(req, res);

        assertEquals(201, res.getStatus());
        JSONAssert.assertEquals("{\"message\":\"Product added successfully\"," +
                        "\"product\":\"ProductRecord[id=1, " +
                        "name=P1, " +
                        "category=COMPUTERS, " +
                        "rating=1, " +
                        "createdAt=2021-01-01, lastModified=2021-01-01]\"}",
                res.getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    public void addProductAlreadyExistingThrowProductConflictException() throws Exception {
        ProductRecord p = getSingleProduct();
        Mockito.when(warehouseService.addProduct(p)).thenReturn(false);
        MockHttpRequest req = MockHttpRequest.post("/products");
        req.contentType("application/json");

        String jsonPayload = objectMapper.writeValueAsString(p);
        req.content(jsonPayload.getBytes("UTF-8"));

        MockHttpResponse res = new MockHttpResponse();

        dispatcher.invoke(req, res);

        assertEquals(409, res.getStatus());
        JSONAssert.assertEquals(
                "{\"ProductConflictException error\":\"Product with id and/or name already exists\"}",
                res.getContentAsString(), JSONCompareMode.LENIENT);
    }

//    @Test
//    public void addProductWithConstraintViolationsReturnListOfViolations() throws Exception {
//        ProductRecord badProduct = new ProductRecord(
//                -1, "", null, 11, null, null);
//        Mockito.when(warehouseService.addProduct(badProduct)).thenReturn(false);
//        MockHttpRequest req = MockHttpRequest.post("/products");
//        req.contentType("application/json");
//
//        String jsonPayload = objectMapper.writeValueAsString(badProduct);
//        req.content(jsonPayload.getBytes("UTF-8"));
//
//        MockHttpResponse res = new MockHttpResponse();
//        dispatcher.invoke(req, res);
//
//        assertEquals(400, res.getStatus());
//    }

    @Test
    public void getProductWithIdReturnGoodValuesIfItExist() throws Exception {
        Mockito.when(warehouseService.getProduct(1)).thenReturn(Optional.of(getSingleProduct()));
        MockHttpRequest req = MockHttpRequest.get("/products/1");
        MockHttpResponse res = new MockHttpResponse();

        dispatcher.invoke(req, res);

        assertEquals(200, res.getStatus());
        JSONAssert.assertEquals(
                "{\"id\":1,\"name\":\"P1\"," +
                        "\"category\":\"COMPUTERS\",\"rating\":1," +
                        "\"createdAt\":\"2021-01-01\",\"lastModified\":\"2021-01-01\"}",
                res.getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    public void getProductWithIdReturnBadValuesIfItDoesNotExist() throws Exception {
        int targetId = 1;
        Mockito.when(warehouseService.getProduct(targetId)).thenReturn(Optional.empty());
        MockHttpRequest req = MockHttpRequest.get("/products/1");
        MockHttpResponse res = new MockHttpResponse();

        dispatcher.invoke(req, res);

        assertEquals(404, res.getStatus());
        JSONAssert.assertEquals("{\"ProductNotFoundException error\":" +
                        "\"Product with id " + targetId + " could not be found\"}",
                res.getContentAsString(), JSONCompareMode.LENIENT);
    }

}