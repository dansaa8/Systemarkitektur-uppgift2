package com.example.warehousewebserver.service;

import com.example.warehousewebserver.entities.Product;
import com.example.warehousewebserver.entities.ProductRecord;

import java.time.LocalDate;
import java.util.List;

public final class ProductUpdater {
    private ProductUpdater() {}

    public static boolean updateProduct(List<Product> pList, ProductRecord p) {

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
}
