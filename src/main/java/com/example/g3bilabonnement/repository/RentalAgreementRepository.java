package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    private final RowMapper<RentalAgreement> rentalAgreementRowMapper = (rs, rowNum) -> {
        RentalAgreement rentalAgreement = new RentalAgreement();

        rentalAgreement.setId(rs.getInt("id"));

        //Her hentes det specifikke bil objekt fra rental agreement
        int carId = rs.getInt("car_id");
        CarRepository carRepository = new CarRepository();
        Car car = carRepository.getById(carId);
        rentalAgreement.setCar(car);

        //Her hentes det specifikke subscription objekt
        int subscriptionId = rs.getInt("subscription_id");
        SubscriptionRepository subscriptionRepository = new SubscriptionRepository();
        Subscription subscription = subscriptionRepository.getById(subscriptionId);
        rentalAgreement.setSubscription(subscription);

        //Her hentes det specifikke renter objekt fra databasen
        int renterId = rs.getInt("renter_id");
        RenterRepository renterRepository = new RenterRepository();
        Renter renter = renterRepository.getById(renterId);
        rentalAgreement.setRenter(renter);

        rentalAgreement.setStartDate(rs.getDate("start_date").toLocalDate());
        rentalAgreement.setEndDate(rs.getDate("end_date").toLocalDate());

        return rentalAgreement;
    };

    public RentalAgreement getById(int id){
        String sql = "SELECT * FROM rental_agreement WHERE id =?";
        return jdbcTemplate.queryForObject(sql, rentalAgreementRowMapper, id);
    }




}

