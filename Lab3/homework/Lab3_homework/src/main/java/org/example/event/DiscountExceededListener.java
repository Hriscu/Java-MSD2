package org.example.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DiscountExceededListener {
    private final Logger logger = LoggerFactory.getLogger(DiscountExceededListener.class);

    @EventListener
    public void onDiscountExceeded(DiscountExceededEvent evt) {
        logger.warn("EVENT DiscountExceeded: customer={} order={} discount={}",
                evt.getCustomer().getName(), evt.getOrder().getId(), evt.getDiscountAmount());

    }
}
