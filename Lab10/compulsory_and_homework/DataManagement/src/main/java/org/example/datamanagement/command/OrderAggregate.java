package org.example.datamanagement.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.datamanagement.coreapi.CoreApi.*;

@Aggregate(snapshotTriggerDefinition = "mySnapshotTriggerDefinition")
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String item;
    private Double price;
    private String status;

    public OrderAggregate() {}

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        if (command.getPrice() <= 0) throw new IllegalArgumentException("Price must be positive");

        AggregateLifecycle.apply(new OrderCreatedEvent(
                command.getOrderId(), command.getItem(), command.getPrice(), "CREATED"
        ));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.item = event.getItem();
        this.price = event.getPrice();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand command) {
        AggregateLifecycle.apply(new OrderCompletedEvent(command.getOrderId(), "COMPLETED"));
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CancelOrderCommand command) {
        AggregateLifecycle.apply(new OrderCancelledEvent(command.getOrderId(), command.getReason(), "CANCELLED"));
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        this.status = event.getStatus();
    }
}