package com.tutienda.backend.service;

import com.tutienda.backend.dto.UpdateHeroSlideRequest;
import com.tutienda.backend.exception.HeroSlideNotFoundException;
import com.tutienda.backend.model.HeroSlide;
import com.tutienda.backend.repository.HeroSlideRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HeroSlideService {

    private final HeroSlideRepository heroSlideRepository;

    public List<HeroSlide> getAll() {
        return heroSlideRepository.findAllByOrderByOrdenAsc();
    }

    public HeroSlide updateSlide(Long id, UpdateHeroSlideRequest request) {
        HeroSlide slide = heroSlideRepository.findById(id)
                .orElseThrow(() -> new HeroSlideNotFoundException("Slide no encontrado"));
        updateFields(slide, request);
        log.info("HeroSlide actualizado correctamente: {}", slide);
        return heroSlideRepository.save(slide);
    }

    @Transactional
    public void deleteSlide(Long id) {
        HeroSlide slide = heroSlideRepository.findById(id)
                .orElseThrow(() -> new HeroSlideNotFoundException("Slide no encontrado"));

        heroSlideRepository.delete(slide);

        // Reordenar los restantes para que no queden huecos
        List<HeroSlide> remaining = heroSlideRepository.findAllByOrderByOrdenAsc();

        for (int i = 0; i < remaining.size(); i++) {
            remaining.get(i).setOrden(i);
        }

        heroSlideRepository.saveAll(remaining);
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