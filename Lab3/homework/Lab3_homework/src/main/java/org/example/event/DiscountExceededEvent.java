package org.example.event;

import org.example.domain.Customer;
import org.example.domain.Order;
import org.springframework.context.ApplicationEvent;

public class DiscountExceededEvent extends ApplicationEvent {
    private final Customer customer;
    private final Order order;
    private final double discountAmount;

    public DiscountExceededEvent(Object source, Customer customer, Order order, double discountAmount) {
        super(source);
        this.customer = customer;
        this.order = order;
        this.discountAmount = discountAmount;
    }

    public Customer getCustomer() { return customer; }
    public Order getOrder() { return order; }
    public double getDiscountAmount() { return discountAmount; }
}
