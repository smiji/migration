package com.example.orders;

import java.math.BigDecimal;

import com.example.orders.domain.Order;
import com.example.orders.service.OrderService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(OrderService service) {
        return args -> {
            service.create(newOrder("Asha", "1250.50"));
            service.create(newOrder("Ravi", "99.99"));
            service.create(newOrder("Zoë Müller", "4300.00")); // non-ASCII on purpose (charset test)
        };
    }

    private static Order newOrder(String customer, String value) {
        Order o = new Order();
        o.setCustomerName(customer);
        o.setValue(new BigDecimal(value));
        return o;
    }
}
