package org.example.discount;

import org.example.domain.Customer;
import org.example.domain.DiscountResult;
import org.example.domain.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("none")
public class NoDiscountService implements DiscountService {

    @Override
    public DiscountResult applyDiscount(Order order, Customer customer) {
        return new DiscountResult(0.0, order.getTotalAmount());
    }
}
