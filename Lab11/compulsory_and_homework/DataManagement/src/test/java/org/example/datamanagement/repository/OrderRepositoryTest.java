package org.example.datamanagement.repository;

import org.example.datamanagement.query.OrderSummary; // The Entity
import org.example.datamanagement.query.OrderSummaryRepository; // The Repository

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers // 1. Tells JUnit to look for Docker containers in this class
@DataMongoTest  // 2. Loads only the MongoDB slice of Spring Boot (faster than full app)
class OrderRepositoryTest {

    // 3. Define the MongoDB Docker Container
    // This will download the 'mongo:latest' image and start it automatically
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    private OrderSummaryRepository repository;

    // 4. Connect Spring Boot to the Docker Container
    // By default, Spring connects to localhost:27017. We must overwrite this
    // to connect to the random port that Testcontainers assigned.
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void testCrudOperations() {
        // --- CREATE ---
        OrderSummary order = new OrderSummary();
        order.setOrderId("test-id-999");
        order.setItem("Testcontainers Product");
        order.setPrice(100.0);
        order.setStatus("CREATED");

        repository.save(order);

        // --- READ ---
        Optional<OrderSummary> foundOrder = repository.findById("test-id-999");

        // --- ASSERT ---
        assertTrue(foundOrder.isPresent(), "Order should be found in the container DB");
        assertEquals("Testcontainers Product", foundOrder.get().getItem());
        assertEquals(100.0, foundOrder.get().getPrice());

        // --- DELETE ---
        repository.deleteById("test-id-999");
        assertTrue(repository.findById("test-id-999").isEmpty(), "Order should be deleted");
    }
}