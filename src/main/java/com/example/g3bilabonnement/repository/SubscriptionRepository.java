package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.KilometerOption;
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

        KilometerOption kilometerOption = new KilometerOption();
        kilometerOption.setId(rs.getInt("kilometer_options_id"));
        subscription.setKilometerOption(kilometerOption);

        subscription.setTotalPricePerMonth(rs.getDouble("price_per_month"));
        return subscription;
    };

    public Subscription getById(int id) {
        String sql = "SELECT * FROM subscription WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, subscriptionRowMapper, id);
    }

    // Returns id of the newly created subscription
    public int add(Subscription subscription) {
        String sql = "INSERT INTO subscription (base_price, subscription_type, kilometer_options_id, price_per_month) " +
                     "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                subscription.getBaseSubscriptionPrice(),
                subscription.getSubscriptionType(),
                subscription.getKilometerOption().getId(),
                subscription.getTotalPricePerMonth());

        String getIdSql = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(getIdSql, Integer.class);
    }
}
