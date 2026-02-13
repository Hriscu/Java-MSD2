package org.example.datamanagement.coreapi;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.Value;

public class CoreApi {


    @Value
    public static class CreateOrderCommand {
        @TargetAggregateIdentifier 
        String orderId;
        String item;
        Double price;
    }

    @Value
    public static class ProcessPaymentCommand {
        @TargetAggregateIdentifier
        String paymentId;
        String orderId;
        Double amount;
    }

    @Value
    public static class CompleteOrderCommand {
        @TargetAggregateIdentifier
        String orderId;
    }

    @Value
    public static class CancelOrderCommand {
        @TargetAggregateIdentifier
        String orderId;
        String reason;
    }


    @Value
    public static class OrderCreatedEvent {
        String orderId;
        String item;
        Double price;
        String status; 
    }

    @Value
    public static class PaymentProcessedEvent {
        String paymentId;
        String orderId;
    }

    @Value
    public static class PaymentFailedEvent { 
        String paymentId;
        String orderId;
    }

    @Value
    public static class OrderCompletedEvent {
        String orderId;
        String status; 
    }

    @Value
    public static class OrderCancelledEvent {
        String orderId;
        String reason;
        String status; 
    }
}