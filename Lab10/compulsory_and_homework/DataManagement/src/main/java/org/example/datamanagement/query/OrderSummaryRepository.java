package org.example.datamanagement.query;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSummaryRepository extends MongoRepository<OrderSummary, String> {
}