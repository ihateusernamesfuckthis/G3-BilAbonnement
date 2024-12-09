package com.example.g3bilabonnement.entity;

public class FinalSettlement {

    int id;
    Car car;
    RentalAgreement rentalAgreement;
    DamageReport damageReport;
    double totalDamageReportPrice;
    int totalKilometerDriven;
    double overdrivenKilometerPrice;
    double totalPrice;

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

    public double calculateTotalPrice(){

        //Vi regner det samlede beløb ud fra både lejeperioden + meromkostninger
        double rentAndDamagePrice = (damageReport.getTotalDamagePrice() + rentalAgreement.getTotalPrice());
        this.totalPrice = rentAndDamagePrice + this.overdrivenKilometerPrice;
        return totalPrice;
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

    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    public DamageReport getDamageReport() {
        return damageReport;
    }

    public void setDamageReport(DamageReport damageReport) {
        this.damageReport = damageReport;
    }

    public double getTotalDamageReportPrice() {
        return totalDamageReportPrice;
    }

    public void setTotalDamageReportPrice(double totalDamageReportPrice) {
        this.totalDamageReportPrice = totalDamageReportPrice;
    }

    public int getTotalKilometerDriven() {
        return totalKilometerDriven;
    }

    public void setTotalKilometerDriven(int totalKilometerDriven) {
        this.totalKilometerDriven = totalKilometerDriven;
    }

    public double getOverdrivenKilometerPrice() {
        return overdrivenKilometerPrice;
    }

    public void setOverdrivenKilometerPrice(double overdrivenKilometerPrice) {
        this.overdrivenKilometerPrice = overdrivenKilometerPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}


