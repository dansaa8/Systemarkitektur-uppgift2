package com.example.systemarkitekturuppgift2.service;

import com.example.systemarkitekturuppgift2.entities.Product;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;

import java.time.LocalDate;
import java.util.List;

import static com.example.systemarkitekturuppgift2.util.ProductValidator.alreadyExists;

public class ProductUpdater {

    static boolean updateProduct(List<Product> pList, ProductRecord p) {

        var foundProduct = pList.stream()
                .filter(product -> product.getId() == p.id())
                .findFirst();

        if (foundProduct.isPresent()) {
            updateFields(foundProduct.get(), p);
            return true;
        }
        return false;
    }

    private static void updateFields(Product oldProd, ProductRecord updatedProd) {
        oldProd.setName(updatedProd.name());
        oldProd.setCategory(updatedProd.category());
        oldProd.setRating(updatedProd.rating());
        oldProd.setLastModified(LocalDate.now());
    }

    static boolean insertIntoList(List<Product> pList, ProductRecord p) {
        if (!alreadyExists(pList, p)) {
            pList.add(new Product(p));
            return true;
        }
        return false;
    }
}
