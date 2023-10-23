package com.example.warehousewebserver.service;

import com.example.warehousewebserver.Log;
import com.example.warehousewebserver.entities.Category;
import com.example.warehousewebserver.entities.Product;
import com.example.warehousewebserver.entities.ProductRecord;
import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.Lock;
import jakarta.ejb.Singleton;
import jakarta.ws.rs.Produces;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.warehousewebserver.service.ProductAdder.insertIntoList;
import static com.example.warehousewebserver.service.ProductUpdater.updateProduct;
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
