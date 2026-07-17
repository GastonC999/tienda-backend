package com.tutienda.backend.controller;

import com.tutienda.backend.dto.UpdateHeroSlideRequest;
import com.tutienda.backend.model.HeroSlide;
import com.tutienda.backend.service.HeroSlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slides")
@RequiredArgsConstructor
public class HeroSlideController {

    private final HeroSlideService heroSlideService;

    @GetMapping
    public List<HeroSlide> getAll() {
        return heroSlideService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HeroSlide> update(@PathVariable Long id, @RequestBody UpdateHeroSlideRequest request) {
        return ResponseEntity.ok(heroSlideService.updateSlide(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        heroSlideService.deleteSlide(id);
        return ResponseEntity.noContent().build();
    }
}