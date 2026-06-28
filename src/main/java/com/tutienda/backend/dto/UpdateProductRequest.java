package com.tutienda.backend.dto;

public record UpdateProductRequest(
        String name,
        String description,
        Double price,
        String image,
        String category,
        Integer stock
) {
}