package com.tutienda.backend.factory;

import com.tutienda.backend.dto.TopProductDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StatsFactory {

    public static List<Object[]> orderStatusRows() {
        List<Object[]> rows = new ArrayList<>();
        rows.add(new Object[]{"PENDING", 5L});
        rows.add(new Object[]{"PAID", 10L});
        return rows;
    }

    public static List<Object[]> revenueByCategoryRows() {
        List<Object[]> rows = new ArrayList<>();
        rows.add(new Object[]{"Café", BigDecimal.valueOf(12000)});
        rows.add(new Object[]{"Cultivo", BigDecimal.valueOf(8000)});
        return rows;
    }

    public static List<Object[]> dailyOrdersRows() {
        List<Object[]> rows = new ArrayList<>();
        rows.add(new Object[]{"2026-06-20", 5L});
        rows.add(new Object[]{"2026-06-21", 7L});
        return rows;
    }

    public static List<TopProductDTO> topProducts() {
        return List.of(
                new TopProductDTO("Café Etiopía", 15L),
                new TopProductDTO("Prensa Francesa", 8L)
        );
    }
}
