package com.example.g3bilabonnement.entity;

import com.example.g3bilabonnement.repository.PurchaseAgreementRepository;

public class PurchaseAgreement {

    private int id;
    private Car car;
    private FinalSettlement finalSettlement;
    double finalPrice;
    String pickUpLocation;

    public PurchaseAgreement() {
    }

    public PurchaseAgreement(Car car, String pickupLocation) {
        this.car = car;
        this.pickUpLocation = pickupLocation;
        this.finalPrice = calculateFinalPrice();
    }

    public double calculateFinalPrice() {
        finalPrice = (car.getNetPrice() - finalSettlement.getTotalDamageReportPrice());
        return finalPrice;
    }
    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {this.car = car;}
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
    public int getId() {return id;}
    public void setId(int id) {
        this.id = id;
    }
}
