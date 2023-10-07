package com.example.systemarkitekturuppgift2.entities;

import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.Product;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRecordTest {

    @Test
    public void testEquals() {
        ProductRecord product1 = new ProductRecord(1, "Product A", Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        ProductRecord product2 = new ProductRecord(1, "Product A", Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        ProductRecord product3 = new ProductRecord(2, "Product B", Category.VEHICLES, 5, LocalDate.now(), LocalDate.now());

        // Test equality
        assertEquals(product1, product2);

        // Test inequality
        assertNotEquals(product1, product3);
    }

    @Test
    public void testHashCode() {
        ProductRecord product1 = new ProductRecord(1, "Product A", Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        ProductRecord product2 = new ProductRecord(1, "Product A", Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        ProductRecord product3 = new ProductRecord(2, "Product B", Category.VEHICLES, 5, LocalDate.now(), LocalDate.now());

        // Test hash code equality
        assertEquals(product1.hashCode(), product2.hashCode());

        // Test hash code inequality
        assertNotEquals(product1.hashCode(), product3.hashCode());
    }

    @Test
    public void testToString() {
        ProductRecord product = new ProductRecord(1, "Product A", Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        String expectedToString = "ProductRecord[id=1, name=Product A, category=COMPUTERS, rating=4, createdAt=" + LocalDate.now() + ", lastModified=" + LocalDate.now() + ']';

        // Test toString method
        assertEquals(expectedToString, product.toString());
    }

    @Test
    public void testCopyConstructor() {
        Product product = new Product(1, "Product A", Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        ProductRecord productRecord = new ProductRecord(product);

        // Test that the copy constructor creates an equivalent ProductRecord
        assertEquals(product.getId(), productRecord.id());
        assertEquals(product.getName(), productRecord.name());
        assertEquals(product.getCategory(), productRecord.category());
        assertEquals(product.getRating(), productRecord.rating());
        assertEquals(product.getCreatedAt(), productRecord.createdAt());
        assertEquals(product.getLastModified(), productRecord.lastModified());
    }
}
