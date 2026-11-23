package com.example.orders.service;

import com.example.orders.domain.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private OrderService orderService;   // circular reference back to OrderService

    public void orderCreated(Order order) {
        log.info("Order {} created for {}. Total orders: {}",
                order.getId(), order.getCustomerName(), orderService.countOrders());
    }
}
