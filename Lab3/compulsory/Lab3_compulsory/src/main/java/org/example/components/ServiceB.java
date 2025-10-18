package org.example.components;

import org.springframework.stereotype.Component;

@Component
public class ServiceB {
    public ServiceB() {
        System.out.println("2) ServiceB created (Constructor)");
    }
}
