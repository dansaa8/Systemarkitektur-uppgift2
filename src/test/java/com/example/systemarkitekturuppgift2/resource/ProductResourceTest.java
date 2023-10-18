package com.example.systemarkitekturuppgift2.resource;

import com.example.systemarkitekturuppgift2.MyException;
import com.example.systemarkitekturuppgift2.MyExceptionMapper;
import com.example.systemarkitekturuppgift2.service.WarehouseService;
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

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {

    @Mock
    WarehouseService warehouseService;

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
    }

    @Test
    public void productsReturnsAllProductsWithStatus200() throws Exception {
        Mockito.when(warehouseService.getAllProducts()).thenReturn(Collections.EMPTY_LIST);
        MockHttpRequest request = MockHttpRequest.get("/products");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }
}