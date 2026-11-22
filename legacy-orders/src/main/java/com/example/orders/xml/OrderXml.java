package com.example.orders.xml;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;          // TRAP: removed from JDK 11+
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.example.orders.domain.Order;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderXml {

    private Long id;
    private String customerName;
    private String value;
    private String status;

    public OrderXml() {
    }

    public static String toXml(Order o) {
        OrderXml x = new OrderXml();
        x.id = o.getId();
        x.customerName = o.getCustomerName();
        x.value = String.valueOf(o.getValue());
        x.status = String.valueOf(o.getStatus());
        try {
            Marshaller m = JAXBContext.newInstance(OrderXml.class).createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            m.marshal(x, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}
