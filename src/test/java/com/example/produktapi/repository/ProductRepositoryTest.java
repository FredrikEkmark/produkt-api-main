package com.example.produktapi.repository;

import com.example.produktapi.model.Product;
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

        // given

        // when
        List<Product> products = underTest.findAll();

        // then
        assertFalse(products.isEmpty());
    }

    @Test
    void whenSearchingForAndExistingTitleWithFindByTitle_thenReturnThatProduct() {

        // given
        String title = "En dator";
        underTest.save(new Product(
                title,
                25000.0,
                "electronics",
                "bra att ha",
                "url"));

        // when
        Optional<Product> optionalProduct = underTest.findByTitle(title);

        // then
        assertTrue(optionalProduct.isPresent());
        assertEquals(optionalProduct.get().getTitle(), title);

    }

    @Test
    void whenSearchingForANonExistingTitleWithFindByTitle_thenReturnAnEmptyOptional() {

        // given
        String title = "En dator";

        // when
        Optional<Product> optionalProduct = underTest.findByTitle(title);

        // then
        assertTrue(optionalProduct.isEmpty());
        assertThrows(NoSuchElementException.class, optionalProduct::get);

    }

    @Test
    void whenFindByCategoryIsCalledGivenAnExistingCategory_thenReturnListOfAllProductsWithThatCategory() {

        // given
        String category = "electronics";
        underTest.save(new Product(
                "En Dator",
                25000.0,
                category,
                "bra att ha",
                "url"));

        // when
        List<Product> products = underTest.findByCategory(category);

        // then
        assertFalse(products.isEmpty());
        assertEquals(category, products.get(0).getCategory());

    }

    @Test
    void whenFindByCategoryIsCalledGivenAnNonExistingCategory_thenReturnAnEmptyList() {

        // given
        String category = "En Category som absolut inte finns 894kdu48wjs73kr846";

        // when
        List<Product> categories = underTest.findByCategory(category);

        // then
        assertTrue(categories.isEmpty());
    }

    @Test
    void whenGetAllCategoriesIsCalled_returnListWithCategories() {

        // given
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

        // when
        List<String> categories = underTest.findAllCategories();
        int index1 = categories.indexOf(category1);
        int index2 = categories.indexOf(category2);

        // then
        assertEquals(categories.get(index1), category1);
        assertEquals(categories.get(index2), category2);

    }

}