package com.example.systemarkitekturuppgift2.util;

import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.Product;

import java.time.LocalDate;
import java.util.List;

public final class MockDataGenerator {
    private MockDataGenerator(){};

        public static List<Product> renderProducts(List<Product> pList) {
            pList.add(new Product(1, "Motorcycle",
                    Category.VEHICLES, 4, LocalDate.now(), LocalDate.now()));
            pList.add(new Product(2, "Car",
                    Category.VEHICLES, 6, LocalDate.now(), LocalDate.now()));
            pList.add(new Product(3, "Airplane",
                    Category.VEHICLES, 8, LocalDate.now(), LocalDate.now()));

            pList.add(new Product(4, "Monkey",
                    Category.ANIMALS, 1, LocalDate.now(), LocalDate.now()));
            pList.add(new Product(5, "Dog",
                    Category.ANIMALS, 7, LocalDate.now(), LocalDate.now()));
            pList.add(new Product(6, "Lion",
                    Category.ANIMALS, 3, LocalDate.now(), LocalDate.now()));

            pList.add(new Product(7, "Jeans",
                    Category.CLOTHES, 4, LocalDate.now(), LocalDate.now()));
            pList.add(new Product(8, "Jacket",
                    Category.CLOTHES, 2, LocalDate.now(), LocalDate.now()));
            pList.add(new Product(9, "Hat",
                    Category.CLOTHES, 10, LocalDate.now(), LocalDate.now()));


            pList.add(new Product(10, "Laptop",
                    Category.COMPUTERS, 8, LocalDate.now(), LocalDate.now()));
            pList.add(new Product(11, "Desktop",
                    Category.COMPUTERS, 1, LocalDate.now(), LocalDate.now()));
            pList.add(new Product(12, "Raspberry-Pi",
                    Category.COMPUTERS, 5, LocalDate.now(), LocalDate.now()));

            return pList;
    }
}
