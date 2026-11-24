package com.example.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import com.example.orders.domain.Order;
import com.example.orders.domain.OrderStatus;
import com.example.orders.repo.OrderRepository;
import com.example.orders.service.NotificationService;
import com.example.orders.service.OrderService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository repository;

    @Mock
    NotificationService notificationService;

    @InjectMocks
    OrderService service;

    @Test
    void createSetsStatusAndNotifies() {
        when(repository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        Order o = new Order();
        o.setCustomerName("Test");
        o.setValue(new BigDecimal("10.00"));

        Order saved = service.create(o);

        assertEquals(OrderStatus.NEW, saved.getStatus());
        assertNotNull(saved.getCreatedAt());
        verify(notificationService).orderCreated(saved);
    }
}
