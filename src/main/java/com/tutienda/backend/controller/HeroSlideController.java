package com.tutienda.backend.controller;

import com.tutienda.backend.dto.UpdateHeroSlideRequest;
import com.tutienda.backend.model.HeroSlide;
import com.tutienda.backend.service.HeroSlideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slides")
@Slf4j
@RequiredArgsConstructor
public class HeroSlideController {

    private final HeroSlideService heroSlideService;

    @GetMapping
    public List<HeroSlide> getAll() {
        return heroSlideService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HeroSlide> update(@PathVariable Long id, @RequestBody UpdateHeroSlideRequest request) {
        log.info("entra al controller");
        return ResponseEntity.ok(heroSlideService.updateSlide(id, request));
    }
}