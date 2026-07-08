package com.tutienda.backend.factory;

import com.tutienda.backend.enums.OrderStatus;
import com.tutienda.backend.model.Order;
import com.tutienda.backend.model.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public class OrderFactory {
    public static Order anOrder() {
        Order o = new Order();
        o.setId(1L);
        o.setCustomerName("John Doe");
        o.setCustomerEmail("johndoe@gmail.com");
        o.setTotal(6400.0);
        o.setStatus(OrderStatus.PAID);
        o.setCreatedAt(LocalDateTime.now());
        o.setItems(List.of(anOrderItem()));
        return o;
    }

    public static OrderItem anOrderItem() {
        OrderItem item = new OrderItem();
        item.setId(1L);
        item.setProductId(1L);
        item.setProductName("Café Etiopía");
        item.setPrice(3200.0);
        item.setQuantity(2);
        return item;
    }
}
