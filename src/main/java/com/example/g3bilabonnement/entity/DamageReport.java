package com.example.g3bilabonnement.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DamageReport {

    private int id;
    private LocalDate creationDate;
    private List<DamageSpecification> damageSpecifications = new ArrayList<>();
    private double totalDamagePrice;
    private Car car;
    public DamageReport() {
    }

    public DamageReport(LocalDate creationDate, List<DamageSpecification> damageSpecifications, Car car) {
        this.creationDate = creationDate;
        this.damageSpecifications = damageSpecifications;
        this.totalDamagePrice = calculateTotalDamagePrice();
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<DamageSpecification> getDamageSpecifications() {
        return damageSpecifications;
    }

    public void setDamageSpecifications(List<DamageSpecification> newDamageSpecifications){
        this.damageSpecifications = newDamageSpecifications;
    }

    public double getTotalDamagePrice() {
        return totalDamagePrice;
    }
    public double calculateTotalDamagePrice() {
        double total = 0.0;
        for (DamageSpecification d : damageSpecifications) {
            total += d.getDamagePrice();
        }
        return total;
    }

    public void setTotalDamagePrice(double newTotalDamagePrice) {
        this.totalDamagePrice = newTotalDamagePrice;
    }
    public Car getCar() {
        return car;
    }

    public void setCar(Car newCar) {
        this.car = newCar;
    }
}
