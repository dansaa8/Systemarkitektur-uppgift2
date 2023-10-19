package com.example.systemarkitekturuppgift2.resource;

import com.example.systemarkitekturuppgift2.JacksonConfig;
import com.example.systemarkitekturuppgift2.MyException;
import com.example.systemarkitekturuppgift2.MyExceptionMapper;
import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.Product;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import com.example.systemarkitekturuppgift2.service.WarehouseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
        ExceptionMapper<MyException> mapper = new MyExceptionMapper();
        // Register your custom ExceptionMapper
        dispatcher.getProviderFactory().registerProviderInstance(mapper);
        dispatcher.getProviderFactory().registerProvider(JacksonConfig.class);
    }

    private List<ProductRecord> mockedProductList() {
        return List.of(
                new ProductRecord(1, "P1", Category.COMPUTERS, 1,
                        LocalDate.of(2021, 1, 1),
                        LocalDate.of(2021, 1, 1))
        );
    }

    private ProductRecord mockedSingleProduct() {
        return new ProductRecord(1, "P1", Category.COMPUTERS, 1,
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 1));
    }

    @Test
    public void productsReturnsAllProductsWithStatus200() throws Exception {

        Mockito.when(warehouseService.getAllProducts()).thenReturn(mockedProductList());
        System.out.println(mockedProductList());
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

//    @Test
//    public void addProductReturnsMessage
}