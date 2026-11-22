package com.example.orders;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import com.example.orders.domain.Order;
import com.example.orders.domain.OrderStatus;
import com.example.orders.xml.OrderXml;

import org.junit.jupiter.api.Test;

class OrderXmlTest {

    @Test
    void marshalsToXml() {
        Order o = new Order();
        o.setId(1L);
        o.setCustomerName("Asha");
        o.setValue(new BigDecimal("12.50"));
        o.setStatus(OrderStatus.NEW);

        String xml = OrderXml.toXml(o);

        assertTrue(xml.contains("<customerName>Asha</customerName>"));
    }
}
