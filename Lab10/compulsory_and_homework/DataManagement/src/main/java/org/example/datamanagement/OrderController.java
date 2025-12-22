package org.example.datamanagement;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.datamanagement.query.OrderSummary;
import org.example.datamanagement.query.OrderSummaryRepository;
import org.springframework.web.bind.annotation.*;
import org.example.datamanagement.coreapi.CoreApi.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class OrderController {

    private final CommandGateway commandGateway;
    private final OrderSummaryRepository orderRepository;

    public OrderController(CommandGateway commandGateway, OrderSummaryRepository orderRepository) {
        this.commandGateway = commandGateway;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createOrder(@RequestParam String item, @RequestParam Double price) {
        String orderId = UUID.randomUUID().toString();
        return commandGateway.send(new CreateOrderCommand(orderId, item, price));
    }

    @GetMapping("/all")
    public List<OrderSummary> getOrders() {
        return orderRepository.findAll();
    }
}