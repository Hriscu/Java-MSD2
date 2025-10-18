package org.example.components;

import org.springframework.stereotype.Component;

@Component
public class ServiceA {
    public ServiceA() {
        System.out.println("1) ServiceA created (Constructor)");
    }
}
