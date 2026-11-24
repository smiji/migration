package com.example.orders.web;

import java.util.List;

import javax.validation.Valid;

import com.example.orders.domain.Order;
import com.example.orders.service.OrderService;
import com.example.orders.util.Base64Util;
import com.example.orders.util.ReceiptFormatter;
import com.example.orders.xml.OrderXml;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@Valid @RequestBody Order order) {
        return service.create(order);
    }

    /** Uses JAXB (javax.xml.bind). */
    @GetMapping(value = "/{id}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String xml(@PathVariable Long id) {
        return OrderXml.toXml(service.get(id));
    }

    /** Uses localized date formatting (JDK 8 vs CLDR differences). */
    @GetMapping(value = "/{id}/receipt", produces = MediaType.TEXT_PLAIN_VALUE)
    public String receipt(@PathVariable Long id) {
        Order o = service.get(id);
        return ReceiptFormatter.format(o.getCreatedAt()) + " - " + o.getCustomerName() + " - " + o.getValue();
    }

    /** Uses sun.misc.BASE64Encoder (removed in JDK 9). */
    @GetMapping(value = "/{id}/ref", produces = MediaType.TEXT_PLAIN_VALUE)
    public String reference(@PathVariable Long id) {
        return Base64Util.enc(("ORDER-" + id).getBytes());
    }
}
