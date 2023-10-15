package com.example.systemarkitekturuppgift2.entities;

import com.example.systemarkitekturuppgift2.constraint.CategoryConstraint;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ProductRecord(

        @Min(value = 0, message = "Id should not be less than 0")
        int id,

        @NotBlank(message = "Name can't be null or empty")
        String name,

        @CategoryConstraint
        Category category,

        @Min(value = 0, message = "Rating must not be lower than 0")
        @Max(value = 10, message = "Rating must not be higher than 10")
        int rating,
        LocalDate createdAt,
        LocalDate lastModified) {
    public ProductRecord(Product other) {
        this(
                other.getId(),
                other.getName(),
                other.getCategory(),
                other.getRating(),
                other.getCreatedAt(),
                other.getLastModified());
    }

    public ProductRecord(int id, String name, Category category, int rating, LocalDate createdAt, LocalDate lastModified) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
    }
}

