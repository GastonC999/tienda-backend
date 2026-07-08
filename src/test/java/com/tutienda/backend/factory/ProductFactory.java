package com.tutienda.backend.factory;

import com.tutienda.backend.dto.UpdateProductRequest;
import com.tutienda.backend.model.Product;

import java.util.List;

public class ProductFactory {
    public static List<Product> aProductList() {
        return List.of(aProduct());
    }

    public static Product aProduct() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Café Etiopía");
        p.setDescription("Descripción de prueba");
        p.setPrice(3200.0);
        p.setCategory("Café");
        p.setImage("https://placehold.co/400x400");
        p.setStock(100);
        return p;
    }

    public static UpdateProductRequest anUpdateProductRequest() {
        return new UpdateProductRequest(
                "Café Etiopía Actualizado",
                "Descripción actualizada",
                3500.0,
                "https://placehold.co/400x400",
                "Café",
                90
        );
    }
}