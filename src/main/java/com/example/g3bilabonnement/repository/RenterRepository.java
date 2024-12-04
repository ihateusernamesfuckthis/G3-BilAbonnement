package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.Location;
import com.example.g3bilabonnement.entity.Renter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RenterRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<Renter> renterRowMapper = (rs, rowNum) -> {
        Renter renter = new Renter();
        renter.setId(rs.getInt("id"));
        renter.setFirstname(rs.getString("firstname"));
        renter.setLastname(rs.getString("lastname"));
        Location location = new Location();
        location.setId(rs.getInt("location_id"));
        renter.setLocation(location);
        renter.setEmail(rs.getString("email"));
        renter.setPhoneNumber(rs.getString("phone_number"));
        renter.setCprNumber(rs.getString("cpr_number"));
        renter.setRegNumber(rs.getString("reg_number"));
        renter.setAccountNumber(rs.getString("account_number"));
        return renter;
    };

    public Renter getById(int id) {
        String sql = "SELECT * FROM renter WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, renterRowMapper, id);
    }
}
