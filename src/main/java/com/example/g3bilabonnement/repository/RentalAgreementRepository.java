package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.*;
import com.example.g3bilabonnement.entity.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RentalAgreementRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void add(RentalAgreement rentalAgreement) {
        String sql = "INSERT INTO rental_agreement (car_id, subscription_id, renter_id, pickup_location_id, return_location_id ,start_date, end_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                rentalAgreement.getCar().getId(),
                rentalAgreement.getSubscription().getId(),
                rentalAgreement.getRenter().getId(),
                rentalAgreement.getPickupLocation().getId(),
                rentalAgreement.getReturnLocation().getId(),
                rentalAgreement.getStartDate(),
                rentalAgreement.getEndDate());
    }

    private final RowMapper<RentalAgreement> rentalAgreementRowMapper = (rs, rowNum) -> {
        RentalAgreement rentalAgreement = new RentalAgreement();
        Car car = new Car();
        Renter renter = new Renter();
        Subscription subscription = new Subscription();

        rentalAgreement.setCar(car);
        rentalAgreement.setRenter(renter);
        rentalAgreement.setSubscription(subscription);

        rentalAgreement.setId(rs.getInt("id"));

        //Her hentes det specifikke bil objekt fra rental agreement
        int carId = rs.getInt("car_id");
        car.setId(carId);

        //Her hentes det specifikke subscription objekt
        int subscriptionId = rs.getInt("subscription_id");
        subscription.setId(subscriptionId);

        //Her hentes det specifikke renter objekt fra databasen
        int renterId = rs.getInt("renter_id");
        renter.setId(renterId);

        rentalAgreement.setStartDate(rs.getDate("start_date").toLocalDate());
        rentalAgreement.setEndDate(rs.getDate("end_date").toLocalDate());

        Location pickupLocation = new Location();
        pickupLocation.setId(rs.getInt("pickup_location_id"));
        rentalAgreement.setPickupLocation(pickupLocation);

        Location returnLocation = new Location();
        returnLocation.setId(rs.getInt("return_location_id"));
        rentalAgreement.setReturnLocation(returnLocation);

        rentalAgreement.setHasFinalSettlement(rs.getBoolean("has_final_settlement"));

        return rentalAgreement;
    };

    public RentalAgreement getById(int id) {
        String sql = "SELECT ra.*, (fs.id IS NOT NULL) as has_final_settlement FROM rental_agreement as ra " +
                "LEFT JOIN final_settlement as fs ON ra.id = fs.rental_agreement_id " +
                "WHERE ra.id = ?";
        return jdbcTemplate.queryForObject(sql, rentalAgreementRowMapper, id);
    }

    public List<RentalAgreement> getAll() {
        String sql = "SELECT ra.*, (fs.id IS NOT NULL) as has_final_settlement FROM rental_agreement as ra " +
                "LEFT JOIN final_settlement as fs ON ra.id = fs.rental_agreement_id";
        return jdbcTemplate.query(sql, rentalAgreementRowMapper);
    }

    // Gets rental agreements by date range if the rental agreement is active within the range
    public List<RentalAgreement> getByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT ra.*, (fs.id IS NOT NULL) as has_final_settlement FROM rental_agreement as ra " +
                "LEFT JOIN final_settlement as fs ON ra.id = fs.rental_agreement_id " +
                "WHERE (start_date BETWEEN ? AND ?) " + // Starts within the range
                "   OR (end_date BETWEEN ? AND ?) " + // Ends within the range
                "   OR (start_date <= ? AND end_date >= ?)"; // Starts and ends outside the range, so still ongoing in the range
        return jdbcTemplate.query(sql, rentalAgreementRowMapper, startDate, endDate, startDate, endDate, startDate, endDate);
    }
}
