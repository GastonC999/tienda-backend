package com.tutienda.backend.controller;

import com.tutienda.backend.dto.UpdateStatusRequest;
import com.tutienda.backend.enums.OrderStatus;
import com.tutienda.backend.model.Order;
import com.tutienda.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll(
            @RequestParam(required = false) OrderStatus status) {
        return ResponseEntity.ok(orderService.getAll(status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(orderService.updateStatus(id, request.status()));
    }
}