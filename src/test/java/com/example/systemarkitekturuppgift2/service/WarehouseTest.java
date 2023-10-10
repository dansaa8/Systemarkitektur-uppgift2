package com.example.systemarkitekturuppgift2.service;

import com.example.systemarkitekturuppgift2.entities.Category;
import com.example.systemarkitekturuppgift2.entities.ProductRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WarehouseTest {

    @Test
    void addProductWithEmptyNameReturnFalse() {
        ProductRecord p = new ProductRecord(0, "", Category.VEHICLES, 0, LocalDate.now(), LocalDate.now());
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(false, w.addProduct(p));
    }

    @Test
    void addProductWithNullReferenceReturnFalse() {
        ProductRecord p = null;
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(false, w.addProduct(p));
    }

    @Test
    void addProductWithANameNotEmptyReturnTrue() {
        ProductRecord p = new ProductRecord(0, "Motorcycle", Category.VEHICLES, 0, LocalDate.now(), LocalDate.now());
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(true, w.addProduct(p));
    }

    @Test
    void addProductWithRatingHigherThan10ReturnFalse() {
        ProductRecord p1 = new ProductRecord(0, "Motorcycle", Category.VEHICLES, 11, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(0, "Car", Category.VEHICLES, 13, LocalDate.now(), LocalDate.now());
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(false, w.addProduct(p1));
        assertEquals(false, w.addProduct(p2));
    }

    @Test
    void addProductWithNegativeRatingReturnFalse() {
        ProductRecord p1 = new ProductRecord(0, "Motorcycle", Category.VEHICLES, -1, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(0, "Car", Category.VEHICLES, -6, LocalDate.now(), LocalDate.now());
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(false, w.addProduct(p1));
        assertEquals(false, w.addProduct(p2));
    }

    @Test
    void addProductWithRatingBetween0And10ReturnTrue() {
        ProductRecord p1 = new ProductRecord(1, "Motorcycle", Category.VEHICLES, 0, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(2, "Boat", Category.VEHICLES, 4, LocalDate.now(), LocalDate.now());
        ProductRecord p3 = new ProductRecord(3, "Car", Category.VEHICLES, 10, LocalDate.now(), LocalDate.now());
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(true, w.addProduct(p1));
        assertEquals(true, w.addProduct(p2));
        assertEquals(true, w.addProduct(p3));
    }

    @Test
    void addProdWithNegIdReturnFalse() {
        ProductRecord p1 = new ProductRecord(-1, "Cat", Category.ANIMALS, 2, LocalDate.now(), LocalDate.now());
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(false, w.addProduct(p1));
    }

    @Test
    void addProdWithPosIdReturnTrue() {
        ProductRecord p1 = new ProductRecord(2, "Cat", Category.ANIMALS, 2, LocalDate.now(), LocalDate.now());
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(true, w.addProduct(p1));
    }

    @Test
    void addProdWithTheSameNameReturnFalse() {
        ProductRecord p1 = new ProductRecord(1, "Bird", Category.ANIMALS, 5, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(2, "birD", Category.ANIMALS, 3, LocalDate.now(), LocalDate.now());
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(true, w.addProduct(p1));
        assertEquals(false, w.addProduct(p2));
    }

    @Test
    void addProductWithTheSameIDReturnFalse() {
        ProductRecord p1 = new ProductRecord(1, "Bird", Category.ANIMALS, 5, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(1, "Monkey", Category.ANIMALS, 3, LocalDate.now(), LocalDate.now());
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(true, w.addProduct(p1));
        assertEquals(false, w.addProduct(p2));
    }

    @Test
    void modifyNullProductReturnFalse() {
        ProductRecord p = null;
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(false, w.modifyProduct(p));
    }

    @Test
    void modifyNonExistingProductReturnFalse() {
        ProductRecord p = new ProductRecord(1, "hoRsE", Category.ANIMALS, 5, LocalDate.now(), LocalDate.now());
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertEquals(false, w.modifyProduct(p));
    }

    @Test
    void modifyExistingObjectReturnTrue() {
        ProductRecord p1 = new ProductRecord(1, "hoRsE", Category.ANIMALS, 5, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(1, "HOrSE", Category.VEHICLES, 3, LocalDate.now(), LocalDate.now());
        WarehouseBusinessService w = new WarehouseBusinessService();

        w.addProduct(p1);
        assertEquals(true, w.modifyProduct(p2));
    }

    @Test
    void modifyProductShouldChangeValueOfAProductIfANewProductWithTheSameIDIsSentAsAnArgument() {
        WarehouseBusinessService w = new WarehouseBusinessService();

        ProductRecord p1 = new ProductRecord(1, "Cow", Category.ANIMALS, 5,
                LocalDate.of(2021, 11, 12),
                LocalDate.of(2022, 5, 6));
        w.addProduct(p1);

        ProductRecord p2 = new ProductRecord(1, "Airplane", Category.VEHICLES, 3,
                LocalDate.of(2021, 11, 12),
                LocalDate.of(2022, 5, 6));

        w.modifyProduct(p2);
        w.addProduct(new ProductRecord(2, "Motorcycle", Category.VEHICLES, 0, LocalDate.now(), LocalDate.now()));
        w.addProduct(new ProductRecord(3, "Boat", Category.VEHICLES, 4, LocalDate.now(), LocalDate.now()));
        w.addProduct(new ProductRecord(4, "Car", Category.VEHICLES, 10, LocalDate.now(), LocalDate.now()));

        List<ProductRecord> lastFourRecords = w.getAllProducts()
                .subList(w.getAllProducts().size() - 4,
                        w.getAllProducts().size());

        assertThat(lastFourRecords)
                .extracting("id", "name", "category", "rating")
                .containsExactly(
                        tuple(1, "Airplane", Category.VEHICLES, 3),
                        tuple(2, "Motorcycle", Category.VEHICLES, 0),
                        tuple(3, "Boat", Category.VEHICLES, 4),
                        tuple(4, "Car", Category.VEHICLES, 10)
                );
    }

    @Test
    void getAnEmptyModifiableList() {
        WarehouseBusinessService w = new WarehouseBusinessService();
        assertThat(w.getAllProducts())
                .as("Should return an empty list of no objects have been added")
                .isUnmodifiable()
                .isEmpty();
    }

    @Test
    void getProductWithCorrectProductID() {
        WarehouseBusinessService w = new WarehouseBusinessService();
        ProductRecord p1 = new ProductRecord(4, "Cow", Category.ANIMALS, 5, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(1, "Airplane", Category.VEHICLES, 3, LocalDate.now(), LocalDate.now());
        ProductRecord p3 = new ProductRecord(2, "Jeans", Category.CLOTHES, 7, LocalDate.now(), LocalDate.now());
        ProductRecord p4 = new ProductRecord(5, "Shirt", Category.CLOTHES, 5, LocalDate.now(), LocalDate.now());

        w.addProduct(p1);
        w.addProduct(p2);
        w.addProduct(p3);
        w.addProduct(p4);

        assertThat(w.getProduct(2).orElse(null))
                .as("Should return optional containing object p3")
                .isNotSameAs(p3)
                .isEqualTo(p3);
    }

    @Test
    void getProductWithInvalidProductID() {
        WarehouseBusinessService w = new WarehouseBusinessService();
        ProductRecord p1 = new ProductRecord(4, "Cow", Category.ANIMALS, 5, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(1, "Airplane", Category.VEHICLES, 3, LocalDate.now(), LocalDate.now());
        ProductRecord p3 = new ProductRecord(2, "Jeans", Category.CLOTHES, 7, LocalDate.now(), LocalDate.now());
        ProductRecord p4 = new ProductRecord(5, "Shirt", Category.CLOTHES, 5, LocalDate.now(), LocalDate.now());

        w.addProduct(p1);
        w.addProduct(p2);
        w.addProduct(p3);
        w.addProduct(p4);

        assertThat(w.getProduct(3))
                .as("Should return an empty optional")
                .isEmpty()
                .isNotPresent();
    }

    @Test
    void getUnmodifiableListOfProductsThatWereAddedToTheList() {
        WarehouseBusinessService w = new WarehouseBusinessService();
        ProductRecord p1 = new ProductRecord(4, "Cow", Category.ANIMALS, 5, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(1, "Airplane", Category.VEHICLES, 3, LocalDate.now(), LocalDate.now());
        ProductRecord p3 = new ProductRecord(2, "Jeans", Category.CLOTHES, 0, LocalDate.now(), LocalDate.now());
        w.addProduct(p1);
        w.addProduct(p2);
        w.addProduct(p3);

        assertThat(w.getAllProducts())
                .as("Should contain the three objects added above as an unmodifiable list")
                .hasSize(3)
//                .contains(p1, p2, p3)
                .isUnmodifiable();
    }

    @Test
    void getAllProductsOfASpecificCategory() {
        WarehouseBusinessService w = new WarehouseBusinessService();
        ProductRecord p1 = new ProductRecord(4, "Jeans", Category.CLOTHES, 5, LocalDate.now(), LocalDate.now());
        ProductRecord p2 = new ProductRecord(2, "Bear", Category.ANIMALS, 7, LocalDate.now(), LocalDate.now());
        ProductRecord p3 = new ProductRecord(3, "Dog", Category.ANIMALS, 5, LocalDate.now(), LocalDate.now());
        ProductRecord p4 = new ProductRecord(1, "Airplane", Category.VEHICLES, 3, LocalDate.now(), LocalDate.now());
        ProductRecord p5 = new ProductRecord(7, "Cat", Category.ANIMALS, 5, LocalDate.now(), LocalDate.now());
        ProductRecord p6 = new ProductRecord(5, "Alligator", Category.ANIMALS, 5, LocalDate.now(), LocalDate.now());


        w.addProduct(p1);
        w.addProduct(p2);
        w.addProduct(p3);
        w.addProduct(p4);
        w.addProduct(p5);
        w.addProduct(p6);

        Category targetType = Category.ANIMALS;

        assertThat(w.getProductsByCategory(targetType))
                .as("Should contain 4 products (p2, p3, p5, p6), " +
                        "sorted in alphabetic order by name:" +
                        " p6(Alligator), p2(Bear), p5(Cat), p3(Dog)")
                .hasSize(4)
                .containsSequence(p6, p2, p5, p3)
                .doesNotContain(p1, p4)
                .isUnmodifiable();
    }


    @Test
    public void getAllProductsWithACategoryThatDoesntExistReturnEmptyList() {
        WarehouseBusinessService w = new WarehouseBusinessService();

        assertThat(w.getProductsByCategory(Category.CLOTHES))
                .as("Should return an empty list")
                .isEmpty();
    }

    @Test
    public void getAllProductsWithAGivenDateShouldReturnAllProductsCreatedAfterThatDate() {
        WarehouseBusinessService w = new WarehouseBusinessService();

        ProductRecord p1 = new ProductRecord(4, "Jeans", Category.CLOTHES, 5,
                LocalDate.of(2023, 7, 11),
                LocalDate.of(2023, 8, 14));

        ProductRecord p2 = new ProductRecord(2, "Bear", Category.ANIMALS, 7,
                LocalDate.of(2023, 5, 11),
                LocalDate.of(2023, 6, 16));

        ProductRecord p3 = new ProductRecord(3, "Dog", Category.ANIMALS, 5,
                LocalDate.of(2023, 5, 10),
                LocalDate.of(2023, 6, 12));

        ProductRecord p4 = new ProductRecord(1, "Airplane", Category.VEHICLES, 3,
                LocalDate.of(2023, 7, 6),
                LocalDate.of(2023, 9, 8));

        ProductRecord p5 = new ProductRecord(7, "Cat", Category.ANIMALS, 5,
                LocalDate.of(2023, 4, 23),
                LocalDate.of(2023, 5, 20));

        ProductRecord p6 = new ProductRecord(5, "Alligator", Category.ANIMALS, 5,
                LocalDate.of(2023, 2, 11),
                LocalDate.of(2023, 3, 11));

        w.addProduct(p1);
        w.addProduct(p2);
        w.addProduct(p3);
        w.addProduct(p4);
        w.addProduct(p5);
        w.addProduct(p6);

        LocalDate targetDate = LocalDate.of(2023, 5, 10);

        assertThat(w.getProductsCreatedAfterDate(targetDate))
                .as("Method should return all products after a give creation-date.")
                .contains(p1, p2, p4);
    }

    @Test
    public void getAllProductsWithAGivenDateShouldReturnAllProductsModifiedAfterThatDate() {
        WarehouseBusinessService w = new WarehouseBusinessService();

        ProductRecord p1 = new ProductRecord(4, "Jeans", Category.CLOTHES, 5,
                LocalDate.of(2023, 1, 11),
                LocalDate.of(2023, 1, 14));

        ProductRecord p2 = new ProductRecord(2, "Bear", Category.ANIMALS, 7,
                LocalDate.of(2023, 2, 11),
                LocalDate.of(2023, 5, 15));

        ProductRecord p3 = new ProductRecord(3, "Dog", Category.ANIMALS, 5,
                LocalDate.of(2023, 2, 10),
                LocalDate.of(2023, 3, 12));

        ProductRecord p4 = new ProductRecord(1, "Airplane", Category.VEHICLES, 3,
                LocalDate.of(2023, 1, 9),
                LocalDate.of(2023, 2, 18));

        ProductRecord p5 = new ProductRecord(7, "Cat", Category.ANIMALS, 5,
                LocalDate.of(2023, 3, 1),
                LocalDate.of(2023, 3, 13));

        ProductRecord p6 = new ProductRecord(5, "Alligator", Category.ANIMALS, 5,
                LocalDate.of(2023, 2, 11),
                LocalDate.of(2023, 3, 15));

        w.addProduct(p1);
        w.addProduct(p2);
        w.addProduct(p3);
        w.addProduct(p4);
        w.addProduct(p5);
        w.addProduct(p6);

        LocalDate targetDate = LocalDate.of(2023, 3, 12);

        assertThat(w.getProductsModifiedAfterDate(targetDate))
                .as("Method should return all products after a given creation-date.")
                .contains(p2, p5, p6);
    }

    @Test
    public void getCategoriesThatHasAtleastOneProductInProductsList() {
        WarehouseBusinessService w = new WarehouseBusinessService();

        ProductRecord p1 = new ProductRecord(1, "Bear", Category.ANIMALS, 7,
                LocalDate.of(2023, 2, 11),
                LocalDate.of(2023, 5, 15));

        ProductRecord p2 = new ProductRecord(2, "Dog", Category.ANIMALS, 5,
                LocalDate.of(2023, 2, 10),
                LocalDate.of(2023, 3, 12));

        ProductRecord p3 = new ProductRecord(3, "Laptop", Category.COMPUTERS, 5,
                LocalDate.of(2023, 2, 10),
                LocalDate.of(2023, 3, 12));

        w.addProduct(p1);
        w.addProduct(p2);
        w.addProduct(p3);

        Map<String, List<String>> result = w.getExistingCategories();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).containsKey("categories");
        List<String> categories = result.get("categories");

        assertThat(categories)
                .as("List should contain following categories: ANIMALS, COMPUTERS")
                .containsSequence("ANIMALS", "COMPUTERS")
                .hasSize(2)
                .doesNotContain("VEHICLES", "CLOTHES");
    }

    @Test
    public void emptyProductListInWarehouseReturnEmptyCategoryList() {
        WarehouseBusinessService w = new WarehouseBusinessService();

        Map<String, List<String>> result = w.getExistingCategories();
        List<String> categories = result.get("categories");

        assertThat(categories)
                .as("Should return an empty list of categories when no products are present")
                .isEmpty();
    }

    @Test
    public void getAMapWithEveryProductStartingLetterAndCountOccurences() {
        WarehouseBusinessService w = new WarehouseBusinessService();

        ProductRecord p1 = new ProductRecord(4, "Jeans", Category.CLOTHES, 5,
                LocalDate.of(2023, 1, 11),
                LocalDate.of(2023, 1, 14));

        ProductRecord p2 = new ProductRecord(2, "Bear", Category.ANIMALS, 7,
                LocalDate.of(2023, 2, 11),
                LocalDate.of(2023, 5, 15));

        ProductRecord p3 = new ProductRecord(11, "Dog", Category.ANIMALS, 5,
                LocalDate.of(2023, 2, 10),
                LocalDate.of(2023, 3, 12));

        ProductRecord p4 = new ProductRecord(1, "Airplane", Category.VEHICLES, 3,
                LocalDate.of(2023, 1, 9),
                LocalDate.of(2023, 2, 18));

        ProductRecord p5 = new ProductRecord(7, "Cat", Category.ANIMALS, 5,
                LocalDate.of(2023, 3, 1),
                LocalDate.of(2023, 3, 13));

        ProductRecord p6 = new ProductRecord(5, "Alligator", Category.ANIMALS, 5,
                LocalDate.of(2023, 2, 11),
                LocalDate.of(2023, 3, 15));

        ProductRecord p7 = new ProductRecord(3, "Dragon", Category.ANIMALS, 5,
                LocalDate.of(2023, 2, 10),
                LocalDate.of(2023, 3, 12));

        ProductRecord p8 = new ProductRecord(9, "Dell", Category.COMPUTERS, 5,
                LocalDate.of(2023, 2, 10),
                LocalDate.of(2023, 3, 12));

        ProductRecord p9 = new ProductRecord(10, "Jetplane", Category.VEHICLES, 5,
                LocalDate.of(2023, 2, 10),
                LocalDate.of(2023, 3, 12));

        w.addProduct(p1);
        w.addProduct(p2);
        w.addProduct(p3);
        w.addProduct(p4);
        w.addProduct(p5);
        w.addProduct(p6);
        w.addProduct(p7);
        w.addProduct(p8);
        w.addProduct(p9);

        assertThat(w.getFirstLetters())
                .containsEntry("A", 2L)
                .containsEntry("B", 1L)
                .containsEntry("C", 1L)
                .containsEntry("D", 3L)
                .containsEntry("J", 2L);
    }

    @Test
    public void getMapWithEveryProductLetterReturnEmptyMapIfNoProductsArePresent() {
        WarehouseBusinessService w = new WarehouseBusinessService();

        assertThat(w.getFirstLetters())
                .isEmpty();
    }

    @Test
    public void getAllProductsWithMaxRatingCreatedThisMonthSortedByNewestFirst() {
        WarehouseBusinessService w = new WarehouseBusinessService();

        ProductRecord p1 = new ProductRecord(1, "Jeans", Category.CLOTHES, 5,
                LocalDate.of(2023, 9, 11),
                LocalDate.of(2023, 9, 14));

        ProductRecord p2 = new ProductRecord(2, "Bear", Category.ANIMALS, 10,
                LocalDate.of(2023, 9, 11),
                LocalDate.of(2023, 9, 15));

        ProductRecord p3 = new ProductRecord(3, "Dog", Category.ANIMALS, 10,
                LocalDate.of(2023, 8, 10),
                LocalDate.of(2023, 8, 12));

        ProductRecord p4 = new ProductRecord(4, "Airplane", Category.VEHICLES, 3,
                LocalDate.of(2022, 3, 9),
                LocalDate.of(2022, 4, 18));

        ProductRecord p5 = new ProductRecord(5, "Cat", Category.ANIMALS, 10,
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 13));

        ProductRecord p6 = new ProductRecord(6, "Alligator", Category.ANIMALS, 10,
                LocalDate.of(2023, 9, 27),
                LocalDate.of(2023, 9, 28));

        ProductRecord p7 = new ProductRecord(7, "Dragon", Category.ANIMALS, 10,
                LocalDate.of(2023, 2, 10),
                LocalDate.of(2023, 3, 12));

        ProductRecord p8 = new ProductRecord(8, "Dell", Category.COMPUTERS, 10,
                LocalDate.of(2023, 9, 10),
                LocalDate.of(2023, 9, 12));

        ProductRecord p9 = new ProductRecord(9, "Jetplane", Category.VEHICLES, 5,
                LocalDate.of(2023, 9, 10),
                LocalDate.of(2023, 9, 12));

        w.addProduct(p1);
        w.addProduct(p2);
        w.addProduct(p3);
        w.addProduct(p4);
        w.addProduct(p5);
        w.addProduct(p6);
        w.addProduct(p7);
        w.addProduct(p8);
        w.addProduct(p9);

    List<ProductRecord> expectedList = new ArrayList<>();
    expectedList.add(p6); // 27/9
    expectedList.add(p2); // 11/9
    expectedList.add(p8); // 10/9
    expectedList.add(p5); // 1/



    assertThat(w.getProductsWithMaxRating(LocalDate.of(2023,9,1)))
            .isNotSameAs(expectedList)
            .containsExactlyElementsOf(expectedList);
    }

    @Test
    public void getMostPopularProductsReturnEmptyListWhenNoProductsAreFound() {
        WarehouseBusinessService w = new WarehouseBusinessService();
        ProductRecord p1 = new ProductRecord(1, "Jeans", Category.CLOTHES, 5,
                LocalDate.of(2023, 9, 11),
                LocalDate.of(2023, 9, 14));

        ProductRecord p2 = new ProductRecord(2, "Bear", Category.ANIMALS, 10,
                LocalDate.of(2023, 9, 11),
                LocalDate.of(2023, 9, 15));

        ProductRecord p3 = new ProductRecord(3, "Dog", Category.ANIMALS, 10,
                LocalDate.of(2023, 8, 10),
                LocalDate.of(2023, 8, 12));

        ProductRecord p4 = new ProductRecord(4, "Airplane", Category.VEHICLES, 3,
                LocalDate.of(2022, 3, 9),
                LocalDate.of(2022, 4, 18));

        assertThat(w.getProductsWithMaxRating(LocalDate.of(2023, 1, 1)))
                .isEmpty();

    }

}