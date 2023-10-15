package com.example.systemarkitekturuppgift2.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProductRecord(

        @Min(value = 0, message = "Id must not be lower than 0")
        int id,

        @NotNull(message = "Name cannot be null")
                @NotEmpty(message = "Name cannot be empty")
        String name,

        @NotNull(message = "Category cannot be null")
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

