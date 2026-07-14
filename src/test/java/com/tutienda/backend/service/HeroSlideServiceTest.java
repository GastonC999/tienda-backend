package com.tutienda.backend.service;

import com.tutienda.backend.repository.HeroSlideRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class HeroSlideServiceTest {

    @Mock
    private HeroSlideRepository heroSlideRepository;

    @InjectMocks
    private HeroSlideService service;

//    @Test
//    void getAll_WhenSlidesExist_ThenReturnList() {
//
//    }
//
//    @Test
//    void updateSlide_WhenSlideExists_ThenUpdateFields() {
//    }
//
//    @Test
//    void updateSlide_WhenSlideNotFound_ThenThrowHeroSlideNotFoundException(){
//    }
}