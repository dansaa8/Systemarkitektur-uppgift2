package org.example.service;

import org.example.entities.Product;
import org.example.entities.ProductRecord;

import java.time.LocalDate;
import java.util.List;

import static org.example.entities.FieldValidator.isValid;

public class ListUpdater {

    static boolean updateProduct(List<Product> pList, ProductRecord p) {
        if (!isValid(p))
            return false;

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
        if (isValid(p) && !nameExists(pList, p) && !idExists(pList, p)) {
            pList.add(new Product(p));
            return true;
        }
        return false;
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
