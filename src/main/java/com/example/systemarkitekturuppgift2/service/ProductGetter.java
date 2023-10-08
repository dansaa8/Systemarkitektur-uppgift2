package com.example.systemarkitekturuppgift2.service;

import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.Product;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

final class ProductGetter {
    private ProductGetter(){};

    static List<ProductRecord> findProductsByCategory(List<Product> products, Category category) {
        return products.stream()
                .filter(product -> product.getCategory() == category)
                .map(ProductRecord::new)
                .sorted(Comparator.comparing(ProductRecord::name))
                .toList();
    }

    static List<ProductRecord> findProductsCreatedAfterDesiredDate(List<Product> products, LocalDate targetDate) {
        return products.stream()
                .filter(product -> product.getCreatedAt().isAfter(targetDate))
                .map(ProductRecord::new)
                .sorted(Comparator.comparing(ProductRecord::createdAt))
                .toList();
    }

    static List<ProductRecord> findProductsModifiedAfterDesiredDate(List<Product> products, LocalDate targetDate) {
        return products.stream()
                .filter(product -> product.getLastModified().isAfter(targetDate))
                .map(ProductRecord::new)
                .sorted(Comparator.comparing(ProductRecord::lastModified))
                .toList();
    }

    static ProductRecord findProduct(List<Product> pList, int id) {
        return pList.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(ProductRecord::new)
                .orElse(null);
    }

    static Map<String, List<String>> findExistingCategories(List<Product> pList) {
        Map<String, List<String>> result = new HashMap<>();
        List<String> categoryList = pList.stream()
                .map(p -> p.getCategory().toString())
                .distinct()
                .toList();

        result.put("categories", categoryList);
        return result;
    }


    static Map<String, Long> countFirstCharacterOccurrence(List<Product> pList) {
        return pList.stream()
                .map(p -> p.getName().toUpperCase())
                .collect(Collectors.groupingBy(
                        str -> str.substring(0, 1),
                        TreeMap::new,
                        Collectors.counting()
                ));
    }

    static List<ProductRecord> findProductsWithHighestRatingThisMonth(List<Product> pList, LocalDate date) {
        return pList.stream()
                .filter(product -> product.getRating() == 10)
                .filter(product -> product.getCreatedAt().getMonth() == date.getMonth())
                .sorted(Comparator.comparing(Product::getCreatedAt).reversed())
                .map(ProductRecord::new)
                .toList();
    }
}
