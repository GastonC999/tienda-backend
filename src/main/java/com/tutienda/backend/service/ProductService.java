package com.tutienda.backend.service;

import com.tutienda.backend.dto.UpdateProductRequest;
import com.tutienda.backend.exception.ProductNotFoundException;
import com.tutienda.backend.model.Product;
import com.tutienda.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static final String PRODUCT_NOT_FOUND = "Producto no encontrado";
    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product updatedProduct(UpdateProductRequest update, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
        applyUpdate(product, update);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }

    private void applyUpdate(Product product, UpdateProductRequest request) {
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setImage(request.image());
        product.setCategory(request.category());
        product.setStock(request.stock());
    }
}