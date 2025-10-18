package org.example.domain;

public class Order {
    private Long id;
    private Long customerId;
    private double totalAmount;
    private double discountedAmount;
    private double finalAmount;

    public Order(Long id, Long customerId, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.discountedAmount = 0.0;
        this.finalAmount = totalAmount;
    }

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public double getTotalAmount() { return totalAmount; }
    public double getDiscountedAmount() { return discountedAmount; }
    public double getFinalAmount() { return finalAmount; }

    public void applyDiscount(double discount) {
        this.discountedAmount = discount;
        this.finalAmount = Math.max(0.0, this.totalAmount - discount);
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", customerId=" + customerId + ", total=" + totalAmount + ", discount=" + discountedAmount + ", final=" + finalAmount + '}';
    }
}
