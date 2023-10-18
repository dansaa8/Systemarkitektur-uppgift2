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
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {

    @Mock
    WarehouseService warehouseService;

    Dispatcher dispatcher; //simulerad webbserver

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addPerRequestResource(ProductResource.class); // klassen som ska testas.
        dispatcher.getProviderFactory().registerProviderInstance(warehouseService);

        ExceptionMapper<MyException> mapper = new MyExceptionMapper();
        dispatcher.getProviderFactory().registerProviderInstance(mapper);
    }

    @Test
    public void productsReturnsAllProductsWithStatus200() throws Exception {
        //create a mock request and response
        MockHttpRequest request = MockHttpRequest.get("/warehouse/products");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("[]", response.getContentAsString());

    }
}