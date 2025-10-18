package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Aspect;
import org.example.domain.Customer;
import org.example.domain.Order;
import org.example.exception.CustomerNotEligibleException;
import org.example.repository.InMemoryCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DiscountEligibilityAspect {

    private final Logger logger = LoggerFactory.getLogger(DiscountEligibilityAspect.class);
    private final InMemoryCustomerRepository repository;

    public DiscountEligibilityAspect(InMemoryCustomerRepository repository) {
        this.repository = repository;
    }

    @Before("execution(* org.example.discount.DiscountService.applyDiscount(org.example.domain.Order, org.example.domain.Customer)) && args(order, customer)")
    public void checkEligibility(JoinPoint jp, Order order, Customer customer) {
        if (customer == null) {
            throw new CustomerNotEligibleException("Customer is null for order " + (order != null ? order.getId() : "unknown"));
        }

        var maybe = repository.findById(customer.getId());
        if (maybe.isEmpty()) {
            throw new CustomerNotEligibleException("Customer does not exist: " + customer.getId());
        }

        if (!customer.isEligible()) {
            logger.warn("Customer {} is not eligible for discounts", customer.getName());
            throw new CustomerNotEligibleException("Customer not eligible for discounts: " + customer.getName());
        }
    }
}
