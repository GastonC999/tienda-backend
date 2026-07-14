package com.tutienda.backend.dto;

public record UpdateHeroSlideRequest(
        String title,
        String subtitle,
        String imageUrl,
        String cta,
        String href,
        Integer orden
) {
}