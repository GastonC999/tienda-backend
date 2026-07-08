package com.tutienda.backend.service;

import com.tutienda.backend.factory.ProductFactory;
import com.tutienda.backend.dto.UpdateProductRequest;
import com.tutienda.backend.exception.ProductNotFoundException;
import com.tutienda.backend.model.Product;
import com.tutienda.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAll_WhenProductsExist_ThenReturnList() {
        when(productRepository.findAll()).thenReturn(ProductFactory.aProductList());

        List<Product> result = productService.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Café Etiopía", result.getFirst().getName());
    }

    @Test
    void getById_WhenProductExists_ThenReturnProduct() {
        Product product = ProductFactory.aProduct();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getById(1L);

        assertEquals("Café Etiopía", result.getName());
        assertEquals(3200.0, result.getPrice());
        assertEquals("Café", result.getCategory());
    }

    @Test
    void getById_WhenProductNotFound_ThenThrowProductNotFoundException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getById(99L));
    }

    @Test
    void create_WhenValidProduct_ThenSaveAndReturnProduct() {
        Product product = ProductFactory.aProduct();
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.create(product);

        assertEquals("Café Etiopía", result.getName());
        assertEquals(100, result.getStock());
        verify(productRepository).save(product);
    }

    @Test
    void updatedProduct_WhenProductExists_ThenUpdateFields() {
        Product product = ProductFactory.aProduct();
        UpdateProductRequest request = ProductFactory.anUpdateProductRequest();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);

        productService.updatedProduct(request, 1L);

        verify(productRepository).save(any());
        assertEquals("Café Etiopía Actualizado", product.getName());
        assertEquals(3500.0, product.getPrice());
        assertEquals(90, product.getStock());
    }

    @Test
    void updatedProduct_WhenProductNotFound_ThenThrowProductNotFoundException() {
        UpdateProductRequest request = ProductFactory.anUpdateProductRequest();
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.updatedProduct(request, 99L));
    }

    @Test
    void deleteProduct_WhenProductExists_ThenDeleteProduct() {
        Product product = ProductFactory.aProduct();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).delete(product);
    }

    @Test
    void deleteProduct_WhenProductNotFound_ThenThrowProductNotFoundException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct(99L));
    }
}