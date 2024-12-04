package com.example.g3bilabonnement.entity;

public class DamageReport {

    int id;
    double totalDamagePrice = 1000;

    public DamageReport(int id, double totalDamagePrice) {
        this.id = id;
        this.totalDamagePrice = totalDamagePrice;
    }

    public double getTotalDamagePrice() {
        return totalDamagePrice;
    }

    public void setTotalDamagePrice(double totalDamagePrice) {
        this.totalDamagePrice = totalDamagePrice;
    }

    public DamageReport(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
