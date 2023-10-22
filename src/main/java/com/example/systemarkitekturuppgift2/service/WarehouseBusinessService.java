package com.example.systemarkitekturuppgift2.service;

import com.example.systemarkitekturuppgift2.Log;
import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.Product;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import com.example.systemarkitekturuppgift2.util.MockDataGenerator;
import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.Lock;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.systemarkitekturuppgift2.service.ProductAdder.insertIntoList;
import static com.example.systemarkitekturuppgift2.service.ProductUpdater.updateProduct;
import static jakarta.ejb.ConcurrencyManagementType.BEAN;
import static jakarta.ejb.LockType.READ;
import static jakarta.ejb.LockType.WRITE;

@ConcurrencyManagement(BEAN)
@Singleton
@Log
public class WarehouseBusinessService implements WarehouseService {
    private final List<Product> products;

    public WarehouseBusinessService() {
        this.products = new ArrayList<>();
//        MockDataGenerator.renderProducts(products);
    }

    @Produces
    public static WarehouseBusinessService create() {
        return new WarehouseBusinessService();
    }

    @Lock(WRITE)
    public boolean addProduct(ProductRecord p) {
        return (insertIntoList(products, p));
    }

    @Lock(WRITE)
    public boolean modifyProduct(ProductRecord p) {
        return updateProduct(products, p);
    }

    @Lock(READ)
    public Optional<ProductRecord> getProduct(int id) {
        return Optional.ofNullable(ProductGetter.findProduct(products, id));
    }

    @Lock(READ)
    public List<ProductRecord> getAllProducts() {
        return products.stream().map(ProductRecord::new).toList();
    }

    @Lock(READ)
    public List<ProductRecord> getProductsByCategory(Category desiredCategory) {
        return ProductGetter.findProductsByCategory(products, desiredCategory);
    }

    @Lock(READ)
    public List<ProductRecord> getProductsCreatedAfterDate(LocalDate desiredDate) {
        return ProductGetter.findProductsCreatedAfterDesiredDate(products, desiredDate);
    }

    @Lock(READ)
    public List<ProductRecord> getProductsModifiedAfterDate(LocalDate desiredDate) {
        return ProductGetter.findProductsModifiedAfterDesiredDate(products, desiredDate);
    }

    @Lock(READ)
    public Map<String, List<String>> getExistingCategories() {
        return ProductGetter.findExistingCategories(products);
    }

    @Lock(READ)
    public Map<String, Long> getFirstLetters() {
        return ProductGetter.countFirstCharacterOccurrence(products);
    }

    @Lock(READ)
    public List<ProductRecord> getProductsWithMaxRating(LocalDate date) {
        return ProductGetter.findProductsWithMaxRatingThisMonth(products, date);
    }
}
