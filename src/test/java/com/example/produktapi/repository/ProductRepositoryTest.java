package com.example.produktapi.repository;

import com.example.produktapi.model.Product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository underTest;

    @Test
    void testingOurRepository_findAll() {

        List<Product> products = underTest.findAll();

        assertFalse(products.isEmpty());
    }

    @Test
    void whenSearchingForAndExistingTitleWithFindByTitle_thenReturnThatProduct() {

        //Given
        String title = "En dator";
        underTest.save(new Product(
                title,
                25000.0,
                "electronics",
                "bra att ha",
                "url"));

        // When
        Optional<Product> optionalProduct = underTest.findByTitle(title);

        //Then
        // One way to write it
        assertTrue(optionalProduct.isPresent());
        assertFalse(optionalProduct.isEmpty());
        assertEquals(optionalProduct.get().getTitle(), title);

        // Another way to write it
        Assertions.assertAll(
                ()-> assertTrue(optionalProduct.isPresent()),
                ()-> assertFalse(optionalProduct.isEmpty()),
                ()-> assertEquals(optionalProduct.get().getTitle(), title)
        );
    }

    @Test
    void whenSearchingForANonExistingTitleWithFindByTitle_thenReturnAnEmptyOptional() {

        //Given
        String title = "En dator";

        // When
        Optional<Product> optionalProduct = underTest.findByTitle(title);

        //Then
        // One way to write it
        assertFalse(optionalProduct.isPresent());
        assertTrue(optionalProduct.isEmpty());


        // Another way to write it
        Assertions.assertAll(
                ()-> assertFalse(optionalProduct.isPresent()),
                ()-> assertTrue(optionalProduct.isEmpty()),
                ()-> assertThrows(NoSuchElementException.class, ()-> optionalProduct.get())
        );
    }

    @Test
    void whenFindByCategoryIsCalledGivenAnExistingCategory_thenReturnListOfAllProductsWithThatCategory() {

        //Given
        String category = "electronics";
        underTest.save(new Product(
                "En Dator",
                25000.0,
                category,
                "bra att ha",
                "url"));

        // When
        List<Product> categories = underTest.findByCategory(category);

        // then
        assertFalse(categories.isEmpty());

    }

    @Test
    void whenFindByCategoryIsCalledGivenAnNonExistingCategory_thenReturnAnEmptyList() {

        //Given
        String category = "En Category som absolut inte finns 894kdu48wjs73kr846";

        // When
        List<Product> categories = underTest.findByCategory(category);

        // Then
        assertTrue(categories.isEmpty());
    }

    @Test
    void whenGetAllCategoriesIsCalled_returnListWithCategories() {

        //Given
        String category1 = "electronics";
        String category2 = "men's clothing";
        underTest.save(new Product(
                "En Dator",
                25000.0,
                category1,
                "bra att ha",
                "url"));

        underTest.save(new Product(
                "Fjallraven",
                193.3,
                category2,
                "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"));

        // When
        List<String> categories = underTest.findAllCategories();
        int index1 = categories.indexOf(category1);
        int index2 = categories.indexOf(category2);

        //Then
        assertEquals(categories.get(index1), category1);
        assertEquals(categories.get(index2), category2);
    }

}