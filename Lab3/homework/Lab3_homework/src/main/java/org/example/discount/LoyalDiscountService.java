package org.example.discount;

import org.example.domain.Customer;
import org.example.domain.DiscountResult;
import org.example.domain.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("loyal")
public class LoyalDiscountService implements DiscountService {

    private static final double LOYAL_PERCENT = 0.10;

    @Override
    public DiscountResult applyDiscount(Order order, Customer customer) {
        double discount = 0.0;
        if (customer.isLoyal()) {
            discount = order.getTotalAmount() * LOYAL_PERCENT;
        }
        double finalAmount = Math.max(0.0, order.getTotalAmount() - discount);
        return new DiscountResult(discount, finalAmount);
    }
}
