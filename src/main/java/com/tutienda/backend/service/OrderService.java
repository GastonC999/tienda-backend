package com.tutienda.backend.service;

import com.tutienda.backend.enums.OrderStatus;
import com.tutienda.backend.exception.InsufficientStockException;
import com.tutienda.backend.exception.OrderNotFoundException;
import com.tutienda.backend.exception.ProductNotFoundException;
import com.tutienda.backend.model.Order;
import com.tutienda.backend.model.OrderItem;
import com.tutienda.backend.model.Product;
import com.tutienda.backend.repository.OrderRepository;
import com.tutienda.backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order) {
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Stock insuficiente para el producto " + product.getName());
            }
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
        return orderRepository.save(order);
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada"));
    }

    public List<Order> getAll(OrderStatus status) {
        return status != null
                ? orderRepository.findByStatusOrderByCreatedAtDesc(status)
                : orderRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public Order updateStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada"));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}