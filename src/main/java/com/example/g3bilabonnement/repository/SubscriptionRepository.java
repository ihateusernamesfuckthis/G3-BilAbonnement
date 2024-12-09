package com.example.g3bilabonnement.repository;

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
        subscription.setBaseSubscriptionPrice(rs.getDouble("base_price"));
        subscription.setSubscriptionType(rs.getString("subscription_type"));
        subscription.setAllowedKmPerMonth(rs.getInt("allowed_km_per_month"));
        subscription.setTotalPricePerMonth(rs.getDouble("price_per_month"));
        return subscription;
    };

    public Subscription getById(int id) {
        String sql = "SELECT * FROM subscription WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, subscriptionRowMapper, id);
    }
}
