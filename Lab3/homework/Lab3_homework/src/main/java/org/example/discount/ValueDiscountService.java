package org.example.discount;

import org.example.domain.Customer;
import org.example.domain.DiscountResult;
import org.example.domain.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("value")
public class ValueDiscountService implements DiscountService {

    private static final double THRESHOLD = 1000.0;
    private static final double FIXED_DISCOUNT = 150.0;

    @Override
    public DiscountResult applyDiscount(Order order, Customer customer) {
        double discount = 0.0;
        if (order.getTotalAmount() >= THRESHOLD) {
            discount = FIXED_DISCOUNT;
        }
        double finalAmount = Math.max(0.0, order.getTotalAmount() - discount);
        return new DiscountResult(discount, finalAmount);
    }
}
