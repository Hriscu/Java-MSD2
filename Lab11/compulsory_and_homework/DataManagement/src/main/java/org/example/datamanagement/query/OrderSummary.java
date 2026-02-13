package org.example.datamanagement.query;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class OrderSummary {
    @Id
    private String orderId;
    private String item;
    private Double price;
    private String status;
}