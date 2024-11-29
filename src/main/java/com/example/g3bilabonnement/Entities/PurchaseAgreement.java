package com.example.g3bilabonnement.Entities;

public class PurchaseAgreement {
    private Car car;
    private FinalSettlement finalSettlement;
    double finalPrice;
    String pickUpLocation;

    public PurchaseAgreement(int carId, String pickupLocation) {
    }

    public void calculateFinalPrice() {

        if (car == null || finalSettlement == null) {
            return;
        }
        finalPrice = (car.getNetPrice() - finalSettlement.getTotalPrice());
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
        calculateFinalPrice();
    }

    public FinalSettlement getFinalSettlement() {
        return finalSettlement;
    }

    public void setFinalSettlement(FinalSettlement finalSettlement) {
        this.finalSettlement = finalSettlement;
        calculateFinalPrice();
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }
}
