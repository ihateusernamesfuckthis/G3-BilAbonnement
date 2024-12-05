package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RentalAgreementRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //TODO Teknisk set må Crud ikke eksistere i denne opgave


    public void add(RentalAgreement rentalAgreement) {
        String sql = "INSERT INTO rental_agreement (car_id, subscription_id, renter_id, start_date, end_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                rentalAgreement.getCar().getId(),
                rentalAgreement.getSubscription().getId(),
                rentalAgreement.getRenter().getId(),
                rentalAgreement.getStartDate(),
                rentalAgreement.getEndDate());
    }


}
