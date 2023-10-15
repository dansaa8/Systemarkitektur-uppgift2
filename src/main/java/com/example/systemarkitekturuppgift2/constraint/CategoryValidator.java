package com.example.systemarkitekturuppgift2.constraint;

import com.example.systemarkitekturuppgift2.entities.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoryValidator implements ConstraintValidator<CategoryConstraint, Category> {
    @Override
    public void initialize(CategoryConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Category value, ConstraintValidatorContext context) {
        if (value == null) {
            String allowedCategories = Stream.of(Category.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Category cannot be null. Allowed categories: " + allowedCategories)
                    .addConstraintViolation();

            return false;  // Validation fails for null values
        }

        boolean isValid = Stream.of(Category.values())
                .anyMatch(validCategory -> validCategory == value);

        if (!isValid) {
            String allowedCategories = Stream.of(Category.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid category. Allowed categories: " + allowedCategories)
                    .addConstraintViolation();
        }

        return isValid;
    }

}
