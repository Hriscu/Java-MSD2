package org.example.datamanagement.command;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.datamanagement.coreapi.CoreApi.*;

import java.util.UUID;

@Saga
public class OrderManagementSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderCreatedEvent event) {
        System.out.println("SAGA: Order created. Triggering Payment...");

        try {
            if (event.getItem().contains("fail")) {
                throw new RuntimeException("Payment Rejected");
            }
            commandGateway.send(new CompleteOrderCommand(event.getOrderId()));

        } catch (Exception e) {
            System.out.println("SAGA: Payment failed! Compensating transaction: Cancel Order.");
            commandGateway.send(new CancelOrderCommand(event.getOrderId(), e.getMessage()));
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on(OrderCompletedEvent event) {
        System.out.println("SAGA: Transaction Finished Successfully.");
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on(OrderCancelledEvent event) {
        System.out.println("SAGA: Transaction Rolled Back (Compensated).");
    }
}