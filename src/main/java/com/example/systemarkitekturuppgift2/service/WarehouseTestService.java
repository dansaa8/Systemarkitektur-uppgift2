package com.example.systemarkitekturuppgift2.service;

import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.Product;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import com.example.systemarkitekturuppgift2.util.MockDataGenerator;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.systemarkitekturuppgift2.service.ProductUpdater.insertIntoList;
import static com.example.systemarkitekturuppgift2.service.ProductUpdater.updateProduct;

@ApplicationScoped
public class WarehouseTestService implements WarehouseService {
    private final List<Product> products;

    public WarehouseTestService() {
        this.products = new ArrayList<>();
        MockDataGenerator.renderProducts(products);
    }

    public boolean addProduct(ProductRecord p) {
        return (insertIntoList(products, p));
    }

    public boolean modifyProduct(ProductRecord p) {
        return updateProduct(products, p);
    }

    public Optional<ProductRecord> getProduct(int id) {
        return Optional.ofNullable(ProductGetter.findProduct(products, id));
    }

    public List<ProductRecord> getAllProducts() {
        return products.stream().map(ProductRecord::new).toList();
    }

    public List<ProductRecord> getProductsByCategory(Category desiredCategory) {
        return ProductGetter.findProductsByCategory(products, desiredCategory);
    }

    public List<ProductRecord> getProductsCreatedAfterDate(LocalDate desiredDate) {
        return ProductGetter.findProductsCreatedAfterDesiredDate(products, desiredDate);
    }

    public List<ProductRecord> getProductsModifiedAfterDate(LocalDate desiredDate) {
        return ProductGetter.findProductsModifiedAfterDesiredDate(products, desiredDate);
    }

    public Map<String, List<String>> getExistingCategories() {
        return ProductGetter.findExistingCategories(products);
    }

    public Map<String, Long> getFirstLetters() {
        return ProductGetter.countFirstCharacterOccurrence(products);
    }

    public List<ProductRecord> getProductsWithMaxRating(LocalDate date) {
        return ProductGetter.findProductsWithMaxRatingThisMonth(products, date);
    }
}
