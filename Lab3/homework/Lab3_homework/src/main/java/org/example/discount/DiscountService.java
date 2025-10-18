package org.example.discount;

import org.example.domain.Customer;
import org.example.domain.DiscountResult;
import org.example.domain.Order;

public interface DiscountService {
    DiscountResult applyDiscount(Order order, Customer customer);
}
