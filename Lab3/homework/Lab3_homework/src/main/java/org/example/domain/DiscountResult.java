package org.example.domain;

public class DiscountResult {
    private final double discountAmount;
    private final double finalAmount;

    public DiscountResult(double discountAmount, double finalAmount) {
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
    }

    public double getDiscountAmount() { return discountAmount; }
    public double getFinalAmount() { return finalAmount; }
}
