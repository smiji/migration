package com.example.orders.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;   // TRAP: removed from JDK 11+, comes from jakarta.annotation-api

import com.example.orders.domain.Order;
import com.example.orders.domain.OrderStatus;
import com.example.orders.repo.OrderRepository;
import com.example.orders.util.ReportWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository repository;

    // TRAP: circular dependency (OrderService <-> NotificationService).
    // Allowed in Boot 2.5, fails at startup from Boot 2.6.
    @Autowired
    private NotificationService notificationService;

    @PostConstruct
    void init() {
        log.info("OrderService initialised");
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public long countOrders() {
        return repository.count();
    }

    public Order get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order " + id));
    }

    public Order create(Order order) {
        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(LocalDateTime.now());
        Order saved = repository.save(order);
        notificationService.orderCreated(saved);
        return saved;
    }

    /** TRAP: ReportWriter relies on finalize() to close the file, and uses the platform charset. */
    public File exportReport(File target) throws IOException {
        ReportWriter writer = new ReportWriter(target);
        writer.writeLine("id,customer,value,status");
        for (Order o : findAll()) {
            writer.writeLine(o.getId() + "," + o.getCustomerName() + "," + o.getValue() + "," + o.getStatus());
        }
        return target;   // writer never closed explicitly
    }
}
