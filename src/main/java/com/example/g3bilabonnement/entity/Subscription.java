package com.example.g3bilabonnement.entity;

public class Subscription {
    private int id;
    private double baseSubscriptionPrice;
    private String subscriptionType;
    private int allowedKmPerMonth;
    private double totalPricePerMonth;

    public Subscription() {
    }

    public Subscription(int id, double baseSubscriptionPrice, String subscriptionType,
                        int allowedKmPerMonth, Location pickupLocation, Location returnLocation,
                        double totalPricePerMonth) {
        this.id = id;
        this.baseSubscriptionPrice = baseSubscriptionPrice;
        this.subscriptionType = subscriptionType;
        this.allowedKmPerMonth = allowedKmPerMonth;
        this.totalPricePerMonth = totalPricePerMonth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBaseSubscriptionPrice() {
        return baseSubscriptionPrice;
    }

    public void setBaseSubscriptionPrice(double baseSubscriptionPrice) {
        this.baseSubscriptionPrice = baseSubscriptionPrice;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public int getAllowedKmPerMonth() {
        return allowedKmPerMonth;
    }

    public void setAllowedKmPerMonth(int allowedKmPerMonth) {
        this.allowedKmPerMonth = allowedKmPerMonth;
    }


    public double getTotalPricePerMonth() {
        return totalPricePerMonth;
    }

    public void setTotalPricePerMonth(double totalPricePerMonth) {
        this.totalPricePerMonth = totalPricePerMonth;
    }
}
