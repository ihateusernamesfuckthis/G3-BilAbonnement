package com.example.g3bilabonnement.entity;

public class Subscription {
    private int id;
    private double basePriceForCar;
    private String subscriptionType;
    private int allowedKmPerMonth;
    private Location pickupLocation;
    private Location returnLocation;
    private double pricePerMonth;

    public Subscription() {
    }

    public Subscription(int id, double basePriceForCar, String subscriptionType,
                        int allowedKmPerMonth, Location pickupLocation, Location returnLocation,
                        double pricePerMonth) {
        this.id = id;
        this.basePriceForCar = basePriceForCar;
        this.subscriptionType = subscriptionType;
        this.allowedKmPerMonth = allowedKmPerMonth;
        this.pickupLocation = pickupLocation;
        this.returnLocation = returnLocation;
        this.pricePerMonth = pricePerMonth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBasePriceForCar() {
        return basePriceForCar;
    }

    public void setBasePriceForCar(double basePriceForCar) {
        this.basePriceForCar = basePriceForCar;
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

    public double getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(double pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }
}
