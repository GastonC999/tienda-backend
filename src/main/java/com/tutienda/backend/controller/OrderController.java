package com.tutienda.backend.controller;

import com.tutienda.backend.model.Order;
import com.tutienda.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        Order saved = orderRepository.save(order);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll(
            @RequestParam(required = false) String status) {
        List<Order> orders = status != null
                ? orderRepository.findByStatusOrderByCreatedAtDesc(status)
                : orderRepository.findAllByOrderByCreatedAtDesc();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String newStatus = body.get("status");
        List<String> valid = List.of("PENDING", "PAID", "CANCELLED");

        if (!valid.contains(newStatus)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Estado inválido. Valores permitidos: PENDING, PAID, CANCELLED"));
        }

        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(newStatus);
                    return ResponseEntity.ok(orderRepository.save(order));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}