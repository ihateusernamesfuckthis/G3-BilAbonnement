package com.example.g3bilabonnement.entity;

public class Subscription {
    private int id;
    private double baseSubscriptionPrice;
    private String subscriptionType;
    private int allowedKmPerMonth;
    private Location pickupLocation;
    private Location returnLocation;
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
        this.pickupLocation = pickupLocation;
        this.returnLocation = returnLocation;
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

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Location getReturnLocation() {
        return returnLocation;
    }

    public void setReturnLocation(Location returnLocation) {
        this.returnLocation = returnLocation;
    }

    public double getTotalPricePerMonth() {
        return totalPricePerMonth;
    }

    public void setTotalPricePerMonth(double totalPricePerMonth) {
        this.totalPricePerMonth = totalPricePerMonth;
    }
}
