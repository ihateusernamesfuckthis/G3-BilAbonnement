package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Location;

import com.example.g3bilabonnement.entity.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    // Adds location to database and returns the id of the newly created location
    public int add(Location location) {
        // Insert the new location
        String sql = "INSERT INTO location (address, city, zip_code) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, location.getAddress(), location.getCity(), location.getZipCode());

        // Fetch the ID of the last inserted row
        String getIdSql = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(getIdSql, Integer.class);
    }
}
