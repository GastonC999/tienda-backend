package com.tutienda.backend.dto;

import com.tutienda.backend.enums.OrderStatus;

public record UpdateStatusRequest(OrderStatus status) {
}