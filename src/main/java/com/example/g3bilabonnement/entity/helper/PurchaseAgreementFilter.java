package com.example.g3bilabonnement.entity.helper;

public class PurchaseAgreementFilter {

private String licensePlate;
private double minimumFinalPrice;

public PurchaseAgreementFilter(){}

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getMinimumFinalPrice() {
        return minimumFinalPrice;
    }

    public void setMinimumFinalPrice(double minimumFinalPrice) {
        this.minimumFinalPrice = minimumFinalPrice;
    }
}
