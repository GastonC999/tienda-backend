package com.tutienda.backend.factory;

import com.tutienda.backend.dto.UpdateHeroSlideRequest;
import com.tutienda.backend.model.HeroSlide;

import java.util.List;

public class HeroSlideFactory {

    public static List<HeroSlide> aHeroSlideList() {
        return List.of(aHeroSlide());
    }

    public static HeroSlide aHeroSlide() {
        HeroSlide h = new HeroSlide();
        h.setId(1L);
        h.setTitle("Todo para tu Cultivo");
        h.setSubtitle("Equipamiento profesional para cultivadores");
        h.setImageUrl("https://placehold.co/1200x500");
        h.setCta("Ver cultivo");
        h.setHref("/products?category=Cultivo");
        h.setOrden(3);
        return h;
    }

    public static UpdateHeroSlideRequest anUpdateRequest() {
        return new UpdateHeroSlideRequest(
                "Todo para el cultivo",
                "Equipamiento profesional para tu cultivo",
                "/products?category=Cultivo",
                "Ver cultivo",
                "/products?category=Cultivo",
                3);
    }

    public static HeroSlide createSlide(Long id, int orden) {
        HeroSlide slide = new HeroSlide();
        slide.setId(id);
        slide.setTitle("Slide " + id);
        slide.setSubtitle("Subtitle " + id);
        slide.setCta("Ver más");
        slide.setHref("/products");
        slide.setImageUrl("https://res.cloudinary.com/moccana/slide" + id + ".jpg");
        slide.setOrden(orden);
        return slide;
    }
}