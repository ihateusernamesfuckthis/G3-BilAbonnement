package com.example.g3bilabonnement.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RentalAgreementTest {

    private RentalAgreement rentalAgreement;
    private Subscription subscription;

    @BeforeEach
    void setUp() {
        rentalAgreement = new RentalAgreement();
        subscription = new Subscription();
        rentalAgreement.setSubscription(subscription);
    }

    @Test
    void calculateTotalPrice_ValidDatesAndSubscription_ShouldCalculateCorrectly() {
        rentalAgreement.setStartDate(LocalDate.of(2024, 1, 1));
        rentalAgreement.setEndDate(LocalDate.of(2024, 4, 1));
        subscription.setTotalPricePerMonth(1000.0);

        rentalAgreement.calculateTotalPrice();

        assertEquals(3000.0, rentalAgreement.getTotalPrice());
    }

    @Test
    void calculateTotalPrice_NullStartDate_ShouldNotCalculate() {

        rentalAgreement.setEndDate(LocalDate.of(2024, 4, 1));
        subscription.setTotalPricePerMonth(1000.0);

        rentalAgreement.calculateTotalPrice();

        assertEquals(0.0,rentalAgreement.getTotalPrice());
    }

    @Test
    void calculateTotalPrice_NullEndDate_ShouldNotCalculate() {

        rentalAgreement.setStartDate(LocalDate.of(2024, 1, 1));
        subscription.setTotalPricePerMonth(1000.0);

        rentalAgreement.calculateTotalPrice();

        assertEquals(0.0,rentalAgreement.getTotalPrice());
    }

    @Test
    void calculateTotalPrice_NullSubscription_ShouldNotCalculate() {

        rentalAgreement.setStartDate(LocalDate.of(2024, 1, 1));
        rentalAgreement.setEndDate(LocalDate.of(2024, 4, 1));
        rentalAgreement.setSubscription(null);

        rentalAgreement.calculateTotalPrice();

        assertEquals(0.0,rentalAgreement.getTotalPrice());
    }

    @Test
    void calculateTotalPrice_ZeroMonths_ShouldCalculateZeroTotal() {
        rentalAgreement.setStartDate(LocalDate.of(2024, 1, 1));
        rentalAgreement.setEndDate(LocalDate.of(2024, 1, 1)); // Same day
        subscription.setTotalPricePerMonth(1000.0);

        rentalAgreement.calculateTotalPrice();

        assertEquals(0.0, rentalAgreement.getTotalPrice(), 0.01);
    }
}
