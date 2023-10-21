package com.example.systemarkitekturuppgift2.util;

import com.example.systemarkitekturuppgift2.entities.Product;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;

import java.util.List;

public final class ProductValidator {
    private ProductValidator() {}

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
