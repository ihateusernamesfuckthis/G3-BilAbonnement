package com.example.g3bilabonnement.Entities;

public class FinalSettlement {

    int id;
    Car car;
    DamageReport damageReport;
    Double totalMilage;
    Double totalPrice;

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

    public Double getTotalMilage() {
        return totalMilage;
    }

    public void setTotalMilage(Double totalMilage) {
        this.totalMilage = totalMilage;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
