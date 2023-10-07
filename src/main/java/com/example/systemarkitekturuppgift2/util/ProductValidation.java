package com.example.systemarkitekturuppgift2.util;

import com.example.systemarkitekturuppgift2.entities.Product;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;

import java.util.List;

public final class ProductValidation {
    private ProductValidation() {}

    public static boolean isValid(ProductRecord p) {
        if (p == null) return false;

        return isInRange(p.rating()) &&
                !isNullOrEmpty(p.name()) &&
                p.id() >= 0;
    }
    private static boolean isInRange(int rating) {
        return rating >= 0 && rating <= 10;
    }

    private static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }


    public static boolean alreadyExists(List<Product> pList, ProductRecord p) {
        return nameExists(pList, p) ||
                idExists(pList, p);
    }

    private static boolean nameExists(List<Product> pList, ProductRecord p) {
        return pList.stream()
                .anyMatch(product ->
                        product.getName().equalsIgnoreCase(p.name())
                );
    }

    private static boolean idExists(List<Product> pList, ProductRecord p) {
        return pList.stream()
                .anyMatch(product ->
                        product.getId() == p.id()
                );
    }
}
