package org.example.service;

import org.example.config.DiscountProperties;
import org.example.domain.Customer;
import org.example.domain.DiscountResult;
import org.example.domain.Order;
import org.example.discount.DiscountService;
import org.example.event.DiscountExceededEvent;
import org.example.repository.InMemoryCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final DiscountService discountService;
    private final InMemoryCustomerRepository customerRepository;
    private final ApplicationEventPublisher publisher;
    private final DiscountProperties discountProperties;

    public OrderService(DiscountService discountService,
                        InMemoryCustomerRepository customerRepository,
                        ApplicationEventPublisher publisher,
                        DiscountProperties discountProperties) {
        this.discountService = discountService;
        this.customerRepository = customerRepository;
        this.publisher = publisher;
        this.discountProperties = discountProperties;
    }

    public void placeOrder(Order order) {
        Customer customer = customerRepository.findById(order.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + order.getCustomerId()));

        DiscountResult result = discountService.applyDiscount(order, customer);
        order.applyDiscount(result.getDiscountAmount());

        logger.info("Order placed: {} for customer {} => discount={}, final={}",
                order.getId(), customer.getName(), result.getDiscountAmount(), result.getFinalAmount());

        if (result.getDiscountAmount() > discountProperties.getExceedThreshold()) {
            publisher.publishEvent(new DiscountExceededEvent(this, customer, order, result.getDiscountAmount()));
        }
    }
}
