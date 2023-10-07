package com.example.systemarkitekturuppgift2;

import org.example.entities.Category;
import org.example.entities.ProductRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WarehouseService {
     boolean addProduct(ProductRecord p);
     boolean modifyProduct(ProductRecord p);
     Optional getProduct(int id);
     List getAllProducts();
     List getProductsByCategory(Category desiredCategory);
     List getProductsCreatedAfterDate(LocalDate desiredDate);
     List getProductsModifiedAfterDate(LocalDate desiredDate);
     List getExistingCategories();
     Map getFirstLetters();
     List getProductsWithHighestRating(LocalDate date);

}
