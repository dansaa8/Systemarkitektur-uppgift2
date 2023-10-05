package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.ProductRecord;

import java.time.LocalDate;
import java.util.*;

import static org.example.service.Queries.*;
import static org.example.service.ListUpdater.*;

public class Warehouse {
    private final List<Product> products;

    public Warehouse() {
        this.products = new ArrayList<>();
    }

    public boolean addProduct(ProductRecord p) {
        return (insertIntoList(products, p));
    }

    public boolean modifyProduct(ProductRecord p) {
        return updateProduct(products, p);
    }

    public Optional<ProductRecord> getProduct(int id) {
        return Optional.ofNullable(findProduct(products, id));
    }

    public List<ProductRecord> getAllProducts() {
        return products.stream().map(ProductRecord::new).toList();
    }

    public List<ProductRecord> getAllProducts(Category desiredCategory) {
        return Queries.getProductsInCategory(products, desiredCategory);
    }

    public List<ProductRecord> getAllProducts(DateField desiredDateField, LocalDate targetDate) {
        if (desiredDateField == DateField.CREATED_AT)
            return getProductsCreatedAfterDesiredDate(products, targetDate);
        else
            return getProductsLastModifiedAfterDesiredDate(products,targetDate);
    }

    public List<String> getCategories() {
        return findExistingCategories(products);
    }

    public Map<String, Long> getFirstLetters() {
        return countFirstCharacterOccurrence(products);
    }

    public List<ProductRecord> getProductsWithHighestRating(LocalDate date) {
        return findProductsWithHighestRatingThisMonth(products, date);
    }
}
