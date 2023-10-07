package com.example.systemarkitekturuppgift2.entities;

import java.time.LocalDate;

public record ProductRecord(int id, String name, Category category, int rating, LocalDate createdAt, LocalDate lastModified) {
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

