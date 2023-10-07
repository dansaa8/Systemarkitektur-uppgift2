package com.example.systemarkitekturuppgift2.entities;

import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.Product;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class ProductTest {

    @Test
    public void testConstructorAndGetters() {
        LocalDate createdAt = LocalDate.of(2023, 9, 25);
        LocalDate lastModified = LocalDate.of(2023, 9, 26);

        Product product = new Product(1, "Laptop", Category.COMPUTERS, 4, createdAt, lastModified);

        assertEquals(1, product.getId());
        assertEquals("Laptop", product.getName());
        assertEquals(Category.COMPUTERS, product.getCategory());
        assertEquals(4, product.getRating());
        assertEquals(createdAt, product.getCreatedAt());
        assertEquals(lastModified, product.getLastModified());
    }

    @Test
    public void testSetName() {
        Product product = new Product(1, "Laptop", Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        product.setName("Desktop");

        assertEquals("Desktop", product.getName());
    }

    @Test
    public void testSetCategory() {
        Product product = new Product(1, "Laptop", Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        product.setCategory(Category.VEHICLES);

        assertEquals(Category.VEHICLES, product.getCategory());
    }

    @Test
    public void testSetRating() {
        Product product = new Product(1, "Laptop", Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        product.setRating(5);

        assertEquals(5, product.getRating());
    }

    @Test
    public void testSetLastModified() {
        LocalDate initialLastModified = LocalDate.of(2023, 9, 25);
        LocalDate newLastModified = LocalDate.of(2023, 9, 26);
        Product product = new Product(1, "Laptop", Category.COMPUTERS, 4, LocalDate.now(), initialLastModified);

        product.setLastModified(newLastModified);

        assertEquals(newLastModified, product.getLastModified());
    }

    @Test
    public void testEqualsAndHashCode() {
        Product product1 = new Product(1, "Laptop", Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        Product product2 = new Product(1, "Laptop", Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        Product product3 = new Product(2, "Desktop", Category.VEHICLES, 5, LocalDate.now(), LocalDate.now());

        assertEquals(product1, product2);
        assertNotEquals(product1, product3);

        assertEquals(product1.hashCode(), product2.hashCode());
        assertNotEquals(product1.hashCode(), product3.hashCode());
    }

    @Test
    public void testProductConstructor() {
        LocalDate createdAt = LocalDate.of(2023, 9, 25);
        LocalDate lastModified = LocalDate.of(2023, 9, 30);
        ProductRecord record = new ProductRecord(1, "Example Product",
                Category.COMPUTERS, 4, createdAt, lastModified);
        Product product = new Product(record);

        assertEquals(1, product.getId());
        assertEquals("Example Product", product.getName());
        assertEquals(Category.COMPUTERS, product.getCategory());
        assertEquals(4, product.getRating(), 0.001);
        assertEquals(createdAt, product.getCreatedAt());
        assertEquals(lastModified, product.getLastModified());
    }

    @Test
    public void testProductToString() {
        LocalDate createdAt = LocalDate.of(2023, 9, 25);
        LocalDate lastModified = LocalDate.of(2023, 9, 30);
        ProductRecord record = new ProductRecord(2, "Another Product",
                Category.COMPUTERS, 3, createdAt, lastModified);
        Product product = new Product(record);

        String expectedToString = "Product[id=2, name=Another Product, category=COMPUTERS, rating=3, createdAt=2023-09-25, lastModified=2023-09-30]";
        assertEquals(expectedToString, product.toString());
    }

}
