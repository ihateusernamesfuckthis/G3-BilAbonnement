package com.example.g3bilabonnement.entity;


import java.time.LocalDate;

public class RentalAgreement {
    private int id;
    private Car car;
    private Subscription subscription;
    private Renter renter;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;


    public RentalAgreement() {
    }

    public RentalAgreement(int id, Car car, Subscription subscription, Renter renter, LocalDate startDate, LocalDate endDate, double totalPrice) {
        this.id = id;
        this.car = car;
        this.subscription = subscription;
        this.renter = renter;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public int calculateTotalMonths() {
        int startYear = startDate.getYear();
        int startMonth = startDate.getMonthValue();

        int endYear = endDate.getYear();
        int endMonth = endDate.getMonthValue();

        int yearDifference = endYear - startYear;
        int totalMonths = yearDifference * 12 + (endMonth - startMonth);

        if (endDate.getDayOfMonth() > startDate.getDayOfMonth()) {
            totalMonths++;
        }
        return totalMonths;
    }

    public void calculateTotalPrice(){
        // only try to calculate the total price if both dates and subscription are not null to avoid errors
        if (startDate == null || endDate == null || subscription == null) {
            return;
        }
        int months = calculateTotalMonths();
        double pricePerMonth = subscription.getTotalPricePerMonth();
        this.totalPrice = pricePerMonth * months;
    }

    public double calculateOverdrivenKilometerPrice(int totalKilometerDriven){

        int totalAllowedKilometer = calculateTotalMonths() * subscription.getAllowedKmPerMonth();
        int overdrivenKilometer = totalKilometerDriven - (totalAllowedKilometer);


        return overdrivenKilometer <= 0 ? 0.0 : overdrivenKilometer * 0.75;
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
        calculateTotalPrice();
    }

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        calculateTotalPrice();
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        calculateTotalPrice();
    }

    public double getTotalPrice() {
        calculateTotalPrice();
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
