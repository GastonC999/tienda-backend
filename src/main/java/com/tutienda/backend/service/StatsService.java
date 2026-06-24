package com.tutienda.backend.service;

import com.tutienda.backend.dto.DailyOrderDTO;
import com.tutienda.backend.dto.StatsResponse;
import com.tutienda.backend.dto.TopProductDTO;
import com.tutienda.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final OrderRepository orderRepository;

    public StatsResponse getStats(){
        BigDecimal totalRevenue = orderRepository.getTotalRevenue();

        List<Object[]> statusRows = orderRepository.countOrdersByStatus();
        Map<String, Long> ordersByStatus = new HashMap<>();
        for (Object[] row : statusRows) {
            ordersByStatus.put((String) row[0], (Long) row[1]);
        }

        List<TopProductDTO> topProducts = orderRepository.getTopProducts();

        List<Object[]> categoryRows = orderRepository.getRevenueByCategory();
        Map<String, BigDecimal> revenueByCategory = new HashMap<>();
        for (Object[] row : categoryRows) {
            revenueByCategory.put((String) row[0], (BigDecimal) row[1]);
        }

        List<Object[]> dailyRows = orderRepository.getDailyOrdersLast30Days();
        List<DailyOrderDTO> dailyOrders = dailyRows.stream()
                .map(row -> new DailyOrderDTO(row[0].toString(), ((Number) row[1]).longValue()))
                .toList();

        return new StatsResponse(totalRevenue, ordersByStatus, topProducts, revenueByCategory, dailyOrders);
    }
}
