package org.example.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainComponent {

    private final ServiceA serviceA;

    @Autowired
    private ServiceB serviceB;

    private ServiceC serviceC;

    @Autowired
    public MainComponent(ServiceA serviceA) {
        this.serviceA = serviceA;
        System.out.println("-- Constructor injection: ServiceA injected");
    }

    @Autowired
    private void fieldInjected() {
        System.out.println("-- Field injection: ServiceB injected");
    }

    @Autowired
    public void setServiceC(ServiceC serviceC) {
        this.serviceC = serviceC;
        System.out.println("-- Setter/Method injection: ServiceC injected");
    }

    @Autowired
    public void verify() {
        System.out.println("=> All dependencies are injected successfully!");
    }
}
