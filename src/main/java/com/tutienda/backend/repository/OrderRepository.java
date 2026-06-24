package com.tutienda.backend.repository;

import com.tutienda.backend.dto.TopProductDTO;
import com.tutienda.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderByCreatedAtDesc();
    List<Order> findByStatusOrderByCreatedAtDesc(String status);
    // Total de ventas de órdenes PAID
    @Query("SELECT COALESCE(SUM(o.total), 0) FROM Order o WHERE o.status = 'PAID'")
    BigDecimal getTotalRevenue();

    // Cantidad de órdenes agrupadas por status
    @Query("SELECT o.status, COUNT(o) FROM Order o GROUP BY o.status")
    List<Object[]> countOrdersByStatus();

    // Top 5 productos más vendidos
    @Query("SELECT new com.tutienda.backend.dto.TopProductDTO(oi.product.name, SUM(oi.quantity)) " +
            "FROM OrderItem oi GROUP BY oi.product.name ORDER BY SUM(oi.quantity) DESC LIMIT 5")
    List<TopProductDTO> getTopProducts();

    // Ventas por categoría (solo órdenes PAID)
    @Query("SELECT oi.product.category, SUM(oi.price * oi.quantity) " +
            "FROM OrderItem oi WHERE oi.order.status = 'PAID' GROUP BY oi.product.category")
    List<Object[]> getRevenueByCategory();

    // Órdenes de los últimos 30 días agrupadas por día (query nativa MySQL)
    @Query(value = "SELECT DATE(created_at) as date, COUNT(*) as orderCount " +
            "FROM orders WHERE created_at >= DATE_SUB(NOW(), INTERVAL 30 DAY) " +
            "GROUP BY DATE(created_at) ORDER BY date ASC", nativeQuery = true)
    List<Object[]> getDailyOrdersLast30Days();
}