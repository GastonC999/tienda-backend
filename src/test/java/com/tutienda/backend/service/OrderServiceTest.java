package com.tutienda.backend.service;

import com.tutienda.backend.enums.OrderStatus;
import com.tutienda.backend.exception.InsufficientStockException;
import com.tutienda.backend.exception.OrderNotFoundException;
import com.tutienda.backend.exception.ProductNotFoundException;
import com.tutienda.backend.factory.OrderFactory;
import com.tutienda.backend.factory.ProductFactory;
import com.tutienda.backend.model.Order;
import com.tutienda.backend.model.Product;
import com.tutienda.backend.repository.OrderRepository;
import com.tutienda.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder_WhenOrderIsValid_ThenCreateOrderAndUpdateStock() {
        Order order = OrderFactory.anOrder();
        Product product = ProductFactory.aProduct();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        when(productRepository.save(product))
                .thenReturn(product);
        when(orderRepository.save(order))
                .thenReturn(order);

        Order result = orderService.createOrder(order);

        assertNotNull(result);
        assertEquals(order, result);
        assertEquals(98, product.getStock());

        verify(productRepository).findById(1L);
        verify(productRepository).save(product);
        verify(orderRepository).save(order);
    }

    @Test
    void createOrder_WhenProductDoesNotExist_ThenThrowProductNotFoundException() {
        Order order = OrderFactory.anOrder();

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> orderService.createOrder(order));

        verify(productRepository).findById(1L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_WhenStockIsInsufficient_ThenThrowInsufficientStockException() {
        Order order = OrderFactory.anOrder();
        Product product = ProductFactory.aProduct();
        product.setStock(1);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class,
                () -> orderService.createOrder(order));

        verify(productRepository).findById(1L);
        verify(productRepository, never()).save(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void getById_WhenOrderExists_ThenReturnOrder() {
        Order order = OrderFactory.anOrder();

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        Order result = orderService.getById(1L);

        assertNotNull(result);
        assertEquals(order, result);

        verify(orderRepository).findById(1L);
    }

    @Test
    void getById_WhenOrderDoesNotExist_ThenThrowOrderNotFoundException() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.getById(1L));

        verify(orderRepository).findById(1L);
    }

    @Test
    void getAll_WhenStatusIsNull_ThenReturnAllOrders() {
        List<Order> orders = List.of(OrderFactory.anOrder());

        when(orderRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(orders);

        List<Order> result = orderService.getAll(null);

        assertEquals(orders, result);

        verify(orderRepository).findAllByOrderByCreatedAtDesc();
    }

    @Test
    void getAll_WhenStatusIsProvided_ThenReturnFilteredOrders() {
        List<Order> orders = List.of(OrderFactory.anOrder());

        when(orderRepository.findByStatusOrderByCreatedAtDesc(OrderStatus.PAID))
                .thenReturn(orders);

        List<Order> result = orderService.getAll(OrderStatus.PAID);

        assertEquals(orders, result);

        verify(orderRepository)
                .findByStatusOrderByCreatedAtDesc(OrderStatus.PAID);
    }

    @Test
    void updateStatus_WhenOrderExists_ThenUpdateStatus() {
        Order order = OrderFactory.anOrder();

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));
        when(orderRepository.save(order))
                .thenReturn(order);

        Order result = orderService.updateStatus(1L, OrderStatus.CANCELLED);

        assertEquals(OrderStatus.CANCELLED, result.getStatus());

        verify(orderRepository).findById(1L);
        verify(orderRepository).save(order);
    }

    @Test
    void updateStatus_WhenOrderDoesNotExist_ThenThrowOrderNotFoundException() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.updateStatus(1L, OrderStatus.CANCELLED));

        verify(orderRepository).findById(1L);
    }
}