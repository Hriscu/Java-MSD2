package org.example.repository;

import org.example.domain.Customer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class InMemoryCustomerRepository {
    private final Map<Long, Customer> store = new HashMap<>();

    @PostConstruct
    public void init() {
        store.put(1L, new Customer(1L, "Alice (loyal)", true, true));
        store.put(2L, new Customer(2L, "Bob (normal)", false, true));
        store.put(3L, new Customer(3L, "EvilCorp (not eligible)", false, false));
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Collection<Customer> findAll() {
        return Collections.unmodifiableCollection(store.values());
    }
}
