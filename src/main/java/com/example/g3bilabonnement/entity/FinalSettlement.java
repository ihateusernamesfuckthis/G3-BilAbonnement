package com.example.g3bilabonnement.entity;

public class FinalSettlement {

    int id;
    Car car;
    RentalAgreement rentalAgreement;
    DamageReport damageReport;
    Double totalDamageReportPrice;
    int totalKilometerDriven;
    double overdrivenKilometerPrice;
    Double totalPrice;

    public FinalSettlement(){}

    public FinalSettlement(int id, Car car, RentalAgreement rentalAgreement, DamageReport damageReport, Double totalDamageReportPrice, int totalKilometerDriven, double overdrivenKilometerPrice, Double totalPrice) {
        this.id = id;
        this.car = car;
        this.rentalAgreement = rentalAgreement;
        this.damageReport = damageReport;
        this.totalDamageReportPrice = totalDamageReportPrice;
        this.totalKilometerDriven = totalKilometerDriven;
        this.overdrivenKilometerPrice = overdrivenKilometerPrice;
        this.totalPrice = totalPrice;
    }

    public void calculateTotalPrice(DamageReport damageReport, RentalAgreement rentalAgreement){

        double rentAndDamagePrice = (damageReport.totalDamagePrice + rentalAgreement.getTotalPrice());
        totalPrice = rentAndDamagePrice + overdrivenKilometerPrice;
    }

    public void calculateOverdrivenKilometerPrice(RentalAgreement rentalAgreement){
        int totalAllowedKilometer = (rentalAgreement.calculateTotalMonths() * rentalAgreement.getSubscription().getAllowedKmPerMonth());
        int overdrivenKilometer = totalKilometerDriven - (totalAllowedKilometer);

        if (overdrivenKilometer <= 0) {
            overdrivenKilometerPrice = 0.0;
        }
        overdrivenKilometerPrice = overdrivenKilometer * 0.75;
    }

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
        calculateOverdrivenKilometerPrice(rentalAgreement);
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
        calculateTotalPrice(damageReport, rentalAgreement);
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
