package org.example.datamanagement.query;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import org.example.datamanagement.coreapi.CoreApi.*;

@Component
public class OrderProjection {

    private final OrderSummaryRepository repository;

    public OrderProjection(OrderSummaryRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        System.out.println("CQRS (Mongo): Inserting new order projection...");
        OrderSummary summary = new OrderSummary();
        summary.setOrderId(event.getOrderId());
        summary.setItem(event.getItem());
        summary.setPrice(event.getPrice());
        summary.setStatus(event.getStatus());
        repository.save(summary);
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        System.out.println("CQRS (Mongo): Updating status to COMPLETED...");
        repository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus(event.getStatus());
            repository.save(order);
        });
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        System.out.println("CQRS (Mongo): Updating status to CANCELLED...");
        repository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus(event.getStatus());
            repository.save(order);
        });
    }
}