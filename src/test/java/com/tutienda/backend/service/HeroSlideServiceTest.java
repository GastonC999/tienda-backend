package com.tutienda.backend.service;

import com.tutienda.backend.dto.UpdateHeroSlideRequest;
import com.tutienda.backend.exception.HeroSlideNotFoundException;
import com.tutienda.backend.factory.HeroSlideFactory;
import com.tutienda.backend.model.HeroSlide;
import com.tutienda.backend.repository.HeroSlideRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    @Test
    void deleteSlide_WhenSlideExists_ThenDeletesAndReordersRemaining() {
        // Arrange
        HeroSlide slideToDelete = HeroSlideFactory.createSlide(2L, 1);

        HeroSlide slide1 = HeroSlideFactory.createSlide(1L, 0);
        HeroSlide slide3 = HeroSlideFactory.createSlide(3L, 2);
        HeroSlide slide4 = HeroSlideFactory.createSlide(4L, 3);
        List<HeroSlide> remainingAfterDelete = List.of(slide1, slide3, slide4);

        when(heroSlideRepository.findById(2L)).thenReturn(Optional.of(slideToDelete));
        when(heroSlideRepository.findAllByOrderByOrdenAsc()).thenReturn(remainingAfterDelete);

        // Act
        heroSlideService.deleteSlide(2L);

        // Assert
        verify(heroSlideRepository).delete(slideToDelete);

        ArgumentCaptor<List<HeroSlide>> captor = ArgumentCaptor.forClass(List.class);
        verify(heroSlideRepository).saveAll(captor.capture());

        List<HeroSlide> savedSlides = captor.getValue();
        assertEquals(0, savedSlides.get(0).getOrden());
        assertEquals(1, savedSlides.get(1).getOrden());
        assertEquals(2, savedSlides.get(2).getOrden());
    }

    @Test
    void deleteSlide_WhenSlideDoesNotExist_ThenThrowsHeroSlideNotFoundException() {
        // Arrange
        when(heroSlideRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(HeroSlideNotFoundException.class,
                () -> heroSlideService.deleteSlide(99L));

        verify(heroSlideRepository, never()).delete(any());
        verify(heroSlideRepository, never()).saveAll(any());
    }

    @Test
    void deleteSlide_WhenOnlyOneSlideExists_ThenDeletesWithoutReordering() {
        // Arrange
        HeroSlide onlySlide = HeroSlideFactory.createSlide(1L, 0);

        when(heroSlideRepository.findById(1L)).thenReturn(Optional.of(onlySlide));
        when(heroSlideRepository.findAllByOrderByOrdenAsc()).thenReturn(List.of());

        // Act
        heroSlideService.deleteSlide(1L);

        // Assert
        verify(heroSlideRepository).delete(onlySlide);
        verify(heroSlideRepository).saveAll(List.of());
    }
}