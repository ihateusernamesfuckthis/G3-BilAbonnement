package com.example.g3bilabonnement.entity;

public class PurchaseAgreement {
    private Car car;
    private FinalSettlement finalSettlement;
    double finalPrice;
    String pickUpLocation;

    public PurchaseAgreement(){}

    public PurchaseAgreement(Car car, String pickupLocation) {
        this.car = car;
        this.pickUpLocation = pickupLocation;
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
        if (finalSettlement != null && finalSettlement.getDamageReport() != null) {
            calculateFinalPrice(); // Kun udløs beregning, hvis alle data er klar
        }
    }

    public FinalSettlement getFinalSettlement() {
        return finalSettlement;
    }

    public void setFinalSettlement(FinalSettlement finalSettlement) {
        this.finalSettlement = finalSettlement;
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
