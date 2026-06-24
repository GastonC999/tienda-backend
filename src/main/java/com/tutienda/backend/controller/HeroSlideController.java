package com.tutienda.backend.controller;

import com.tutienda.backend.model.HeroSlide;
import com.tutienda.backend.repository.HeroSlideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slides")
@RequiredArgsConstructor
public class HeroSlideController {

    private final HeroSlideRepository heroSlideRepository;

    @GetMapping
    public List<HeroSlide> getAll(){
        return heroSlideRepository.findAllByOrderByOrdenAsc();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HeroSlide> getById(@PathVariable Long id,  @RequestBody HeroSlide body){
        return heroSlideRepository.findById(id)
                .map(slide ->{
                    slide.setTitle(body.getTitle());
                    slide.setSubtitle(body.getSubtitle());
                    slide.setImageUrl(body.getImageUrl());
                    slide.setCta(body.getCta());
                    slide.setHref(body.getHref());
                    slide.setOrden(body.getOrden());

                    HeroSlide updated = heroSlideRepository.save(slide);
                    return ResponseEntity.ok(updated);
                        })
                .orElse(ResponseEntity.notFound().build());
    }
}
