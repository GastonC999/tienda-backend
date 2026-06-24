package com.tutienda.backend.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record StatsResponse(
        BigDecimal totalRevenue,
        Map<String, Long> ordersByStatus,
        List<TopProductDTO> topProducts,
        Map<String, BigDecimal> revenueByCategory,
        List<DailyOrderDTO> dailyOrders
) {
}