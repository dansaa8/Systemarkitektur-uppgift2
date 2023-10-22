package com.example.systemarkitekturuppgift2.service;

import com.example.systemarkitekturuppgift2.entities.Product;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;

import java.util.List;

public final class ProductAdder {
    private ProductAdder() {}

    public static boolean insertIntoList(List<Product> pList, ProductRecord p) {
        if (!alreadyExists(pList, p)) {
            pList.add(new Product(p));
            return true;
        }
        return false;
    }

    private static boolean alreadyExists(List<Product> pList, ProductRecord p) {
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
