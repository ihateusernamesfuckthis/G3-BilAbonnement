package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Location;
import com.example.g3bilabonnement.entity.Renter;
import com.example.g3bilabonnement.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SubscriptionRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<Subscription> subscriptionRowMapper = (rs, rowNum) -> {
        Subscription subscription = new Subscription();
        subscription.setId(rs.getInt("id"));
        subscription.setBasePriceForCar(rs.getDouble("base_price"));
        subscription.setSubscriptionType(rs.getString("subscription_type"));
        subscription.setAllowedKmPerMonth(rs.getInt("allowed_km_per_month"));
        Location pickupLocation = new Location();
        pickupLocation.setId(rs.getInt("pickup_location_id"));
        subscription.setPickupLocation(pickupLocation);
        Location returnLocation = new Location();
        returnLocation.setId(rs.getInt("return_location_id"));
        subscription.setPickupLocation(returnLocation);
        subscription.setPricePerMonth(rs.getDouble("price_per_month"));
        return subscription;
    };

    public Subscription getById(int id) {
        String sql = "SELECT * FROM subscription WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, subscriptionRowMapper, id);
    }
}
