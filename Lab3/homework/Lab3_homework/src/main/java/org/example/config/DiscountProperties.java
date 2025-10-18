package org.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "discount")
public class DiscountProperties {
    private double exceedThreshold = 100.0;

    public double getExceedThreshold() {
        return exceedThreshold;
    }

    public void setExceedThreshold(double exceedThreshold) {
        this.exceedThreshold = exceedThreshold;
    }
}
