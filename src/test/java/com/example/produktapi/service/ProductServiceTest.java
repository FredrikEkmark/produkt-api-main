package com.example.produktapi.service;

import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.exception.EntityNotFoundException;
import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService underTest;

    @Captor
    ArgumentCaptor<Product> productCaptor;

    @Test
    void whenGetAllProducts_thenExactlyOneInteractionWithRepositoryMethodFindAll() {

        // given

        // When
        underTest.getAllProducts();

        //Then
        verify(productRepository, times(1)).findAll();
        verifyNoMoreInteractions(productRepository);

    }

    @Test
    void whenGetAllCategories_thenExactlyOneInteractionWithRepositoryMethodFindAllCategories() {

        // given

        // When
        underTest.getAllCategories();

        //Then
        verify(productRepository, times(1)).findAllCategories();
        verifyNoMoreInteractions(productRepository);

    }

    @Test
    void whenGivenAnExistingCategoryWhenGetProductsByCategory_thenExactlyOneInteractionWithRepositoryMethodFindByCategory() {

        // given
        String category = "test";

        // when
        underTest.getProductsByCategory(category);

        // then
        verify(productRepository, times(1)).findByCategory(category);
        verifyNoMoreInteractions(productRepository);

    }

    @Test
    void whenGetProductByIdIsGivenAValidID_findByIdIsInvokedOnceAndSameProductIsReturned() {

        // given
        Product product = new Product(
                "Computor",
                35.0,
                "Electronics",
                "Description of item",
                "URL");

        given(productRepository.findById(any())).willReturn(Optional.of(product));

        // when
        Product returnProduct = underTest.getProductById(any());

        // then
        verify(productRepository, times(1)).findById(any());
        verifyNoMoreInteractions(productRepository);
        assertEquals(product, returnProduct);

    }

    @Test
    void whenGetProductByIdIsGivenANonValidID_entityNotFoundExceptionIsThrown() {

        // given
        given(productRepository.findById(any())).willReturn(Optional.empty());

        // then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                // when
                ()-> underTest.getProductById(any()));

        // then
        verify(productRepository, times(1)).findById(any());
        assertEquals("Produkt med id null hittades inte", exception.getMessage());
        verifyNoMoreInteractions(productRepository);


    }

    @Test
    void whenAddingAProduct_thenSaveMethodShouldBeInvokedOnceAndFindByTitleOnceAndTheSameProductUsedAsTheSaveParameterIsTheSameAsAddProduct() {

        // given
        Product product = new Product(
                "Computor",
                35.0,
                "Electronics",
                "Description of item",
                "URL");

        Product product2 = new Product(
                "Computor",
                35.0,
                "Electronics",
                "Description of item",
                "URL");

        // when
        underTest.addProduct(product);

        // then
        verify(productRepository, times(1)).save(productCaptor.capture());
        verify(productRepository, times(1)).findByTitle(any());
        verifyNoMoreInteractions(productRepository);
        assertEquals(product, productCaptor.getValue());
        assertNotEquals(product2, productCaptor.getValue());

    }

    @Test
    void whenAddingProductWithAlreadyExistingDuplicateTitle_thenThrowException() {

        // given
        String title = "Computor";
        Product product = new Product(
                title,
                35.0,
                "Electronics",
                "Description of item",
                "URL");

        given(productRepository.findByTitle(product.getTitle())).willReturn(Optional.of(product));

        // when

        // then
        BadRequestException exception = assertThrows(BadRequestException.class,
                // when
                ()-> underTest.addProduct(product));

        verify(productRepository, times(1)).findByTitle(title);
        verify(productRepository, times(0)).save(product);
        verifyNoMoreInteractions(productRepository);
        assertEquals("En produkt med titeln: " + title + " finns redan", exception.getMessage());

    }

    @Test
    void whenUpdatingProductWithAValidExistingProduct_saveMethodIsCalledOnceAndReturnsTheNewProduct() {

        // given
        Product productNew = new Product(
                "Computor",
                35.0,
                "Electronics",
                "Description of item",
                "URL");

        Product productOld = new Product(
                "Computor",
                33.0,
                "Electronics",
                "Description of item",
                "URL");

        given(productRepository.findById(any())).willReturn(Optional.of(productOld));

        // when
        underTest.updateProduct(productNew, any());

        // then
        verify(productRepository, times(1)).save(productCaptor.capture());
        verify(productRepository, times(1)).findById(any());
        verifyNoMoreInteractions(productRepository);
        assertEquals(productNew, productCaptor.getValue());
    }

    @Test
    void whenUpdatingProductWithANonExistingProduct_saveMethodIsNotCalledAndExceptionIsThrown() {

        // given
        Product productNew = new Product(
                "Computor",
                35.0,
                "Electronics",
                "Description of item",
                "URL");

        given(productRepository.findById(any())).willReturn(Optional.empty());

        // then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                // when
                ()-> underTest.updateProduct(productNew, any()));

        // then
        verify(productRepository, times(0)).save(any());
        verify(productRepository, times(1)).findById(any());
        assertEquals("Produkt med id null hittades inte", exception.getMessage());
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void whenDeletingAnExistingProduct_deleteByIdMethodShouldBeInvokedOnce() {

        // given
        Product product = new Product(
                "Computor",
                35.0,
                "Electronics",
                "Description of item",
                "URL");

        given(productRepository.findById(any())).willReturn(Optional.of(product));

        // when
        underTest.deleteProduct(any());

        // then
        verify(productRepository, times(1)).deleteById(any());
        verify(productRepository, times(1)).findById(any());
        verifyNoMoreInteractions(productRepository);

    }

    @Test
    void whenDeletingAnNonExistingProduct_entityNotFoundExceptionIsThrown() {

        // given
        given(productRepository.findById(any())).willReturn(Optional.empty());

        // when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                // when
                ()-> underTest.deleteProduct(any()));

        // then
        verify(productRepository, times(0)).deleteById(any());
        verify(productRepository, times(1)).findById(any());
        assertEquals("Produkt med id null hittades inte", exception.getMessage());
        verifyNoMoreInteractions(productRepository);

    }
}