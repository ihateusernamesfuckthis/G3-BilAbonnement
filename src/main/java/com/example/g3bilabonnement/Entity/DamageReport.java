package com.example.g3bilabonnement.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DamageReport {
    private LocalDate creationDate;
    private List<DamageSpecification> damageSpecifications = new ArrayList<>();
    private double totalDamagePrice;
    private Car car;
    public DamageReport() {
    }

    public DamageReport(Date creationDate, List<DamageSpecification> damageSpecifications, double totalDamagePrice, Car car) {
        this.creationDate = LocalDate.now();;
        this.damageSpecifications = damageSpecifications;
        this.totalDamagePrice = totalDamagePrice;
        this.car = car;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate newCreationDate) {
        this.creationDate = newCreationDate;
    }

    public List<DamageSpecification> getDamageSpecification() {
        return damageSpecifications;
    }

    public void setDamageSpecifications(List<DamageSpecification> newDamageSpecifications){
        this.damageSpecifications = newDamageSpecifications;
    }

    public double getTotalDamagePrice() {
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
