package com.example.g3bilabonnement.entity.helper;

public class PurchaseAgreementFilter {

private String vehicleNumber;
private Double minimumFinalPrice;

public PurchaseAgreementFilter(){}

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Double getMinimumFinalPrice() {
        return minimumFinalPrice;
    }

    public void setMinimumFinalPrice(Double minimumFinalPrice) {
        this.minimumFinalPrice = minimumFinalPrice;
    }
}
