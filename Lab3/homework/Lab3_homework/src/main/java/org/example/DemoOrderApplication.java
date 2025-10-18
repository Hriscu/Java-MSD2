package org.example;

import org.example.domain.Customer;
import org.example.domain.Order;
import org.example.repository.InMemoryCustomerRepository;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class DemoOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoOrderApplication.class, args);
    }

    @org.springframework.context.annotation.Bean
    CommandLineRunner demoRunner(OrderService orderService, InMemoryCustomerRepository repo) {
        return args -> {
            System.out.println("=== DEMO START ===");
            repo.findAll().forEach(c -> System.out.println("Client în repo: " + c));

            Customer loyal = repo.findById(1L).orElseThrow();
            Customer normal = repo.findById(2L).orElseThrow();
            Customer company = repo.findById(3L).orElseThrow();

            Order o1 = new Order(100L, loyal.getId(), 200.0);
            orderService.placeOrder(o1);

            Order o2 = new Order(101L, normal.getId(), 1200.0);
            orderService.placeOrder(o2);

            Order o3 = new Order(102L, company.getId(), 50.0);
            try {
                orderService.placeOrder(o3);
            } catch (Exception ex) {
                System.out.println("Order failed: " + ex.getMessage());
            }

            System.out.println("=== DEMO END ===");
        };
    }
}
