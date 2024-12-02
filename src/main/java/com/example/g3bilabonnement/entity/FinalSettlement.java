package com.example.g3bilabonnement.entity;

public class FinalSettlement {

    int id;
    Car car;
    RentalAgreement rentalAgreement;
    DamageReport damageReport;
    Double totalDamageReportPrice;
    int totalKilometerDriven;
    Double totalPrice = 5000.0;

    public FinalSettlement(){}

    public Double getTotalDamageReportPrice() {
        return totalDamageReportPrice;
    }

    public void setTotalDamageReportPrice(Double totalDamageReportPrice) {
        this.totalDamageReportPrice = totalDamageReportPrice;
    }

    public int getTotalKilometerDriven() {
        return totalKilometerDriven;
    }

    public void setTotalKilometerDriven(int totalKilometerDriven) {
        this.totalKilometerDriven = totalKilometerDriven;
    }

    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public DamageReport getDamageReport() {
        return damageReport;
    }

    public void setDamageReport(DamageReport damageReport) {
        this.damageReport = damageReport;
    }


    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
