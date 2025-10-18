package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.domain.Customer;
import org.example.domain.DiscountResult;
import org.example.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DiscountLoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(DiscountLoggingAspect.class);

    @AfterReturning(pointcut = "execution(* org.example.discount.DiscountService.applyDiscount(..))",
            returning = "result")
    public void logDiscount(JoinPoint jp, Object result) {
        String method = jp.getSignature().toShortString();
        Object[] args = jp.getArgs();

        Customer customer = null;
        Order order = null;
        if (args != null && args.length >= 2) {
            if (args[0] instanceof org.example.domain.Order) {
                order = (Order) args[0];
            }
            if (args[1] instanceof Customer) {
                customer = (Customer) args[1];
            }
        }
        double discountAmt = 0.0;
        if (result instanceof DiscountResult) {
            discountAmt = ((DiscountResult) result).getDiscountAmount();
        }

        String customerName = (customer != null) ? customer.getName() : "unknown";
        logger.info("Discount applied | method={} | customer={} | discount={}", method, customerName, discountAmt);
    }
}
