package com.example.systemarkitekturuppgift2.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductRecordConstraintsTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.byProvider(HibernateValidator.class)
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();
    }

    @Test
    public void testIdConstraintWithNegativeValueExpectViolation() {
        ProductRecord p = new ProductRecord(-1, "Example Product",
                Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());

        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id should not be less than 0");
    }

    @Test
    public void testIdConstraintWithZeroOrPositiveValueExpectNoViolation() {
        ProductRecord p1 = new ProductRecord(0, "Example Product",
                Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(1, "Example Product",
                Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        List<ProductRecord> products = new ArrayList<>(Arrays.asList(p1, p2));

        Set<ConstraintViolation<List<ProductRecord>>> violations = validator.validate(products);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void testNameConstraintWithNullValueExpectViolation() {
        ProductRecord p = new ProductRecord(0, null,
                Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Name can't be null or empty");
    }

    @Test
    public void testNameConstraintWithEmptyValueExpectViolation() {
        ProductRecord p = new ProductRecord(0, " ",
                Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Name can't be null or empty");
    }

    @Test
    public void testNameConstraintWithValueExpectNoViolation() {
        ProductRecord p = new ProductRecord(0, "Raspberry Pi",
                Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void testCategoryConstraintWithNullValueExpectViolation() {
        ProductRecord p = new ProductRecord(0, "Raspberry Pi",
                null, 4, LocalDate.now(), LocalDate.now());
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Category cannot be null. " +
                        "Allowed categories: COMPUTERS, VEHICLES, ANIMALS, CLOTHES");
    }

    @Test
    public void testCategoryConstraintWithOkValueExpectNoViolation() {
        ProductRecord p = new ProductRecord(0, "Raspberry Pi",
                Category.COMPUTERS, 4, LocalDate.now(), LocalDate.now());
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void testRatingConstraintWithNegativeValueExpectViolation() {
        ProductRecord p = new ProductRecord(0, "Raspberry Pi",
                Category.COMPUTERS, -1, LocalDate.now(), LocalDate.now());
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Rating must not be lower than 0");
    }

    @Test
    public void testRatingConstraintWithTooHighValueExpectViolation() {
        ProductRecord p = new ProductRecord(0, "Raspberry Pi",
                Category.COMPUTERS, 11, LocalDate.now(), LocalDate.now());
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Rating must not be higher than 10");
    }

    @Test
    public void testRatingConstraintWithOkValuesExpectNoViolation() {
        ProductRecord p1 = new ProductRecord(0, "Example Product",
                Category.COMPUTERS, 0, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(1, "Example Product",
                Category.COMPUTERS, 10, LocalDate.now(), LocalDate.now());
        List<ProductRecord> products = new ArrayList<>(Arrays.asList(p1, p2));

        Set<ConstraintViolation<List<ProductRecord>>> violations = validator.validate(products);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void testCreatedAtConstraintWithNullDateExpectViolation() {
        ProductRecord p = new ProductRecord(0, "Raspberry Pi",
                Category.COMPUTERS, 1, null, LocalDate.now());
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("createdAt cannot be null");
    }

    @Test
    public void testCreatedAtConstraintOkDateExpectNoViolation() {
        ProductRecord p = new ProductRecord(0, "Raspberry Pi",
                Category.COMPUTERS, 1, LocalDate.now(), LocalDate.now());
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void testLastModifiedConstraintWithNullDateExpectViolation() {
        ProductRecord p = new ProductRecord(0, "Raspberry Pi",
                Category.COMPUTERS, 1, LocalDate.now(), null);
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("lastModified cannot be null");
    }

    @Test
    public void testLastModifiedConstraintOkDateExpectNoViolation() {
        ProductRecord p = new ProductRecord(0, "Raspberry Pi",
                Category.COMPUTERS, 1, LocalDate.now(), LocalDate.now());
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(p);

        assertThat(violations.size()).isEqualTo(0);
    }

}
