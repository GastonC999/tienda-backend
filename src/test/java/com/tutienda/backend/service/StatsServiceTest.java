package com.tutienda.backend.service;

import com.tutienda.backend.dto.StatsResponse;
import com.tutienda.backend.factory.StatsFactory;
import com.tutienda.backend.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    void getStats_WhenStatisticsExist_ThenReturnStatsResponse() {

        when(orderRepository.getTotalRevenue())
                .thenReturn(BigDecimal.valueOf(20000));

        when(orderRepository.countOrdersByStatus())
                .thenReturn(StatsFactory.orderStatusRows());

        when(orderRepository.getTopProducts())
                .thenReturn(StatsFactory.topProducts());

        when(orderRepository.getRevenueByCategory())
                .thenReturn(StatsFactory.revenueByCategoryRows());

        when(orderRepository.getDailyOrdersLast30Days())
                .thenReturn(StatsFactory.dailyOrdersRows());

        StatsResponse result = statsService.getStats();

        assertNotNull(result);

        assertEquals(BigDecimal.valueOf(20000), result.totalRevenue());

        assertEquals(5L, result.ordersByStatus().get("PENDING"));
        assertEquals(10L, result.ordersByStatus().get("PAID"));

        assertEquals(2, result.topProducts().size());

        assertEquals(BigDecimal.valueOf(12000),
                result.revenueByCategory().get("Café"));

        assertEquals(2, result.dailyOrders().size());

        verify(orderRepository).getTotalRevenue();
        verify(orderRepository).countOrdersByStatus();
        verify(orderRepository).getTopProducts();
        verify(orderRepository).getRevenueByCategory();
        verify(orderRepository).getDailyOrdersLast30Days();
    }
}