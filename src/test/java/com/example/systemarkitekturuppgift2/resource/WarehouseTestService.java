package com.example.systemarkitekturuppgift2.resource;

import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.Product;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import com.example.systemarkitekturuppgift2.service.WarehouseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WarehouseTestService implements WarehouseService {
    @Override
    public boolean addProduct(ProductRecord p) {
        return false;
    }

    @Override
    public boolean modifyProduct(ProductRecord p) {
        return false;
    }

    @Override
    public Optional getProduct(int id) {
        return Optional.empty();
    }

    @Override
    public List getAllProducts() {
        return List.of(
                new Product(10, "Laptop",
                        Category.COMPUTERS, 8,
                        LocalDate.of(2023, 1, 24),
                        LocalDate.of(2023, 2, 7)),
                new Product(2, "Car",
                        Category.VEHICLES, 6,
                        LocalDate.of(2023, 4, 4),
                        LocalDate.of(2023, 5, 5))

        );
    }

    @Override
    public List getProductsByCategory(Category desiredCategory) {
        return List.of(
                new Product(1, "Laptop",
                        Category.COMPUTERS, 8,
                        LocalDate.of(2023, 1, 24),
                        LocalDate.of(2023, 2, 7)),
                new Product(2, "Desktop",
                        Category.COMPUTERS, 1,
                        LocalDate.of(2023, 6, 5),
                        LocalDate.of(2023, 6, 16)
                )
        );

    }

    @Override
    public List getProductsCreatedAfterDate(LocalDate desiredDate) {
        return null;
    }

    @Override
    public List getProductsModifiedAfterDate(LocalDate desiredDate) {
        return null;
    }

    @Override
    public Map<String, List<String>> getExistingCategories() {
        return null;
    }

    @Override
    public Map getFirstLetters() {
        return null;
    }

    @Override
    public List getProductsWithMaxRating(LocalDate date) {
        return null;
    }
}
