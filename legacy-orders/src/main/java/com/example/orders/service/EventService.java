package com.example.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.orders.domain.Order;

@Service
class EventService {

    @Autowired
    private NotificationService notificationService;

    public void orderCreatedEvent(Order order,long count){
        notificationService.orderCreated(order,count);
    }
}
