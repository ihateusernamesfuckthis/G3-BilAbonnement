package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.KilometerOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KilometerOptionsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<KilometerOption> kilometerOptionRowMapper = (rs, rowNum) -> {
        KilometerOption option = new KilometerOption();
        option.setId(rs.getInt("id"));
        option.setKilometersPerMonth(rs.getInt("kilometers_per_month"));
        option.setPricePerMonth(rs.getDouble("price_per_month"));
        return option;
    };

    public List<KilometerOption> getKilometerOptions() {
        String sql = "SELECT id, kilometers_per_month, price_per_month FROM kilometer_options";
        return jdbcTemplate.query(sql, kilometerOptionRowMapper);
    }

    public KilometerOption getById(int id) {
        String sql = "SELECT id, kilometers_per_month, price_per_month FROM kilometer_options WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, kilometerOptionRowMapper, id);
    }
}
