package com.example.systemarkitekturuppgift2.util;

import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.Product;

import java.time.LocalDate;
import java.util.List;

public final class MockDataGenerator {
    private MockDataGenerator(){};

        public static List<Product> renderProducts(List<Product> pList) {
            pList.add(new Product(1, "Motorcycle",
                    Category.VEHICLES, 4,
                    LocalDate.of(2023, 8, 4),
                    LocalDate.of(2023, 9, 5)));
            pList.add(new Product(2, "Car",
                    Category.VEHICLES, 6,
                    LocalDate.of(2023, 4, 4),
                    LocalDate.of(2023, 5, 5)));
            pList.add(new Product(3, "Airplane",
                    Category.VEHICLES, 8,
                    LocalDate.of(2023, 2, 22),
                    LocalDate.of(2023, 3, 17)));

            pList.add(new Product(4, "Monkey",
                    Category.ANIMALS, 1,
                    LocalDate.of(2023, 1, 7),
                    LocalDate.of(2023, 1, 21)));
            pList.add(new Product(5, "Dog",
                    Category.ANIMALS, 7,
                    LocalDate.of(2023, 4, 19),
                    LocalDate.of(2023, 6, 25)));
            pList.add(new Product(6, "Lion",
                    Category.ANIMALS, 3,
                    LocalDate.of(2023, 8, 26),
                    LocalDate.of(2023, 9, 2)));

            pList.add(new Product(7, "Jeans",
                    Category.CLOTHES, 4,
                    LocalDate.of(2023, 1, 6),
                    LocalDate.of(2023, 7, 26)));

            pList.add(new Product(8, "Jacket",
                    Category.CLOTHES, 2,
                    LocalDate.of(2023, 3, 12),
                    LocalDate.of(2023, 5, 24)));
            pList.add(new Product(9, "Hat",
                    Category.CLOTHES, 10,
                    LocalDate.of(2023, 4, 5),
                    LocalDate.of(2023, 4, 9)));


            pList.add(new Product(10, "Laptop",
                    Category.COMPUTERS, 8,
                    LocalDate.of(2023, 1, 24),
                    LocalDate.of(2023, 2, 7)));
            pList.add(new Product(11, "Desktop",
                    Category.COMPUTERS, 1,
                    LocalDate.of(2023, 6, 5),
                    LocalDate.of(2023, 6, 16)));
            pList.add(new Product(12, "Raspberry-Pi",
                    Category.COMPUTERS, 5,
                    LocalDate.of(2023, 4, 13),
                    LocalDate.of(2023, 5, 3)));

            return pList;
    }
}
