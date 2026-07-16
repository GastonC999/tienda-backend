package com.tutienda.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateHeroSlideRequest(
        @NotBlank
        String title,

        @NotBlank
        String subtitle,

        @NotBlank
        String imageUrl,

        @NotBlank
        String cta,

        @NotBlank
        String href,

        @NotNull
        Integer orden
) {
}