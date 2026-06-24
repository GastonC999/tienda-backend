package com.tutienda.backend.repository;

import com.tutienda.backend.model.HeroSlide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeroSlideRepository extends JpaRepository<HeroSlide, Long> {
    List<HeroSlide> findAllByOrderByOrdenAsc();
}
