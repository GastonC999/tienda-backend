package com.tutienda.backend.service;

import com.tutienda.backend.dto.UpdateHeroSlideRequest;
import com.tutienda.backend.exception.HeroSlideNotFoundException;
import com.tutienda.backend.model.HeroSlide;
import com.tutienda.backend.repository.HeroSlideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeroSlideService {

    private final HeroSlideRepository heroSlideRepository;

    public List<HeroSlide> getAll() {
        return heroSlideRepository.findAllByOrderByOrdenAsc();
    }

    public HeroSlide updatedSlide(Long id, UpdateHeroSlideRequest request) {
        HeroSlide slide = heroSlideRepository.findById(id)
                .orElseThrow(() -> new HeroSlideNotFoundException("Slide no encontrado"));
        updateFields(slide, request);
        return heroSlideRepository.save(slide);
    }

    private void updateFields(HeroSlide slide, UpdateHeroSlideRequest request) {
        slide.setTitle(request.title());
        slide.setSubtitle(request.subtitle());
        slide.setImageUrl(request.imageUrl());
        slide.setCta(request.cta());
        slide.setHref(request.href());
        slide.setOrden(request.orden());
    }
}