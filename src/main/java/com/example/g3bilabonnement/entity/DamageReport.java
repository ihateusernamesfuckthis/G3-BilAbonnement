package com.example.g3bilabonnement.entity;

import java.time.LocalDate;
import java.util.Date;

public class DamageReport {

    int id;

    Car car;

    LocalDate creationDate;
    double totalDamagePrice = 1000;

    public DamageReport(int id, Car car, LocalDate creationDate, double totalDamagePrice) {
        this.id = id;
        this.car = car;
        this.creationDate = creationDate;
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
