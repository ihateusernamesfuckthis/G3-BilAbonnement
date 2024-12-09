package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.SubscriptionAddon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriptionAddonRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<SubscriptionAddon> subscriptionAddonRowMapper = (rs, rowNum) -> {
        SubscriptionAddon subscription = new SubscriptionAddon();
        subscription.setId(rs.getInt("id"));
        subscription.setName(rs.getString("name"));
        subscription.setPricePerMonth(rs.getDouble("price_per_month"));
        return subscription;
    };
    
    
    public List<SubscriptionAddon> getAll() {
        String sql = "SELECT * FROM SubscriptionAddon";
        return jdbcTemplate.query(sql, subscriptionAddonRowMapper);
    }
    

    public List<SubscriptionAddon> findBySubscriptionId(Integer subscriptionId) {
        String sql = "SELECT a.id, a.name, a.price_per_month FROM SubscriptionAddon a " +
                "JOIN Subscription_SubscriptionAddon sa ON a.id = sa.addon_id " +
                "WHERE sa.subscription_id = ?";

        return jdbcTemplate.query(sql, subscriptionAddonRowMapper, subscriptionId);
    }

    public void addSubscriptionAddonToSubscription(int subscriptionId, int addonId) {
        String sql = "INSERT INTO Subscription_SubscriptionAddon (subscription_id, addon_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, subscriptionId, addonId);
    }
}