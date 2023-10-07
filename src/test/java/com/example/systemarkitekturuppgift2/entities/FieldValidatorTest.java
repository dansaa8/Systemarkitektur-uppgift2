package com.example.systemarkitekturuppgift2.entities;
import com.example.systemarkitekturuppgift2.util.ProductValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class FieldValidatorTest {

    @Test
    public void testValidProductRecord() {
        ProductRecord validProduct = new ProductRecord(1, "Valid Product",
                Category.COMPUTERS, 5, LocalDate.now(), LocalDate.now());
        Assertions.assertTrue(ProductValidation.isValid(validProduct));
    }

    @Test
    public void testNullProductRecord() {
        assertFalse(ProductValidation.isValid(null));
    }

    @Test
    public void testEmptyProductName() {
        ProductRecord emptyNameProduct = new ProductRecord(2, "",
                Category.VEHICLES, 8, LocalDate.now(), LocalDate.now());
        assertFalse(ProductValidation.isValid(emptyNameProduct));
    }

    @Test
    public void testNegativeProductId() {
        ProductRecord negativeIdProduct = new ProductRecord(-1, "Negative ID Product",
                Category.ANIMALS, 7, LocalDate.now(), LocalDate.now());
        assertFalse(ProductValidation.isValid(negativeIdProduct));
    }

    @Test
    public void testRatingBelowRange() {
        ProductRecord belowRangeRatingProduct = new ProductRecord(3, "Below Range Rating",
                Category.CLOTHES, -1, LocalDate.now(), LocalDate.now());
        assertFalse(ProductValidation.isValid(belowRangeRatingProduct));
    }

    @Test
    public void testRatingAboveRange() {
        ProductRecord aboveRangeRatingProduct = new ProductRecord(4, "Above Range Rating",
                Category.COMPUTERS, 11, LocalDate.now(), LocalDate.now());
        assertFalse(ProductValidation.isValid(aboveRangeRatingProduct));
    }

    @Test
    public void testValidProductWithNullName() {
        ProductRecord validProductWithNullName = new ProductRecord(5, null,
                Category.VEHICLES, 9, LocalDate.now(), LocalDate.now());
        assertFalse(ProductValidation.isValid(validProductWithNullName));
    }

}
