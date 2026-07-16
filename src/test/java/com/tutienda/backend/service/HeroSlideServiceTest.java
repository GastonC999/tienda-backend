package com.tutienda.backend.service;

import com.tutienda.backend.dto.UpdateHeroSlideRequest;
import com.tutienda.backend.exception.HeroSlideNotFoundException;
import com.tutienda.backend.factory.HeroSlideFactory;
import com.tutienda.backend.model.HeroSlide;
import com.tutienda.backend.repository.HeroSlideRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroSlideServiceTest {

    @Mock
    private HeroSlideRepository heroSlideRepository;

    @InjectMocks
    private HeroSlideService heroSlideService;

    @Test
    void getAll_WhenSlidesExist_ThenReturnList() {
        List<HeroSlide> slides = HeroSlideFactory.aHeroSlideList();

        when(heroSlideRepository.findAllByOrderByOrdenAsc())
                .thenReturn(slides);

        List<HeroSlide> result = heroSlideService.getAll();

        assertEquals(slides, result);

        verify(heroSlideRepository).findAllByOrderByOrdenAsc();
    }

    @Test
    void updateSlide_WhenSlideExists_ThenUpdateFields() {
        HeroSlide slide = HeroSlideFactory.aHeroSlide();
        UpdateHeroSlideRequest request = HeroSlideFactory.anUpdateRequest();

        when(heroSlideRepository.findById(1L))
                .thenReturn(Optional.of(slide));
        when(heroSlideRepository.save(slide))
                .thenReturn(slide);

        HeroSlide result = heroSlideService.updateSlide(1L, request);

        assertNotNull(result);
        assertEquals(request.title(), result.getTitle());
        assertEquals(request.subtitle(), result.getSubtitle());
        assertEquals(request.imageUrl(), result.getImageUrl());
        assertEquals(request.cta(), result.getCta());
        assertEquals(request.href(), result.getHref());
        assertEquals(request.orden(), result.getOrden());

        verify(heroSlideRepository).findById(1L);
        verify(heroSlideRepository).save(slide);
    }

    @Test
    void updateSlide_WhenSlideDoesNotExist_ThenThrowHeroSlideNotFoundException() {
        UpdateHeroSlideRequest request = HeroSlideFactory.anUpdateRequest();

        when(heroSlideRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(HeroSlideNotFoundException.class,
                () -> heroSlideService.updateSlide(1L, request));

        verify(heroSlideRepository).findById(1L);
        verify(heroSlideRepository, never()).save(any());
    }
}