package com.example.g3bilabonnement.entity;

public class KilometerOption {
    private int id;
    private int kilometersPerMonth;
    private double pricePerMonth;

    public KilometerOption(int id, int kilometersPerMonth, double pricePerMonth) {
        this.id = id;
        this.kilometersPerMonth = kilometersPerMonth;
        this.pricePerMonth = pricePerMonth;
    }

    public KilometerOption() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKilometersPerMonth() {
        return kilometersPerMonth;
    }

    public void setKilometersPerMonth(int kilometersPerMonth) {
        this.kilometersPerMonth = kilometersPerMonth;
    }

    public double getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(double pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }
}
