package com.example.systemarkitekturuppgift2.util;

import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import jakarta.validation.*;

import java.util.Set;

public class BeanValidator {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public BeanValidator() {}

    public void validateProduct(ProductRecord productBean) {
        Set<ConstraintViolation<ProductRecord>> violations = validator.validate(productBean);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }
}
