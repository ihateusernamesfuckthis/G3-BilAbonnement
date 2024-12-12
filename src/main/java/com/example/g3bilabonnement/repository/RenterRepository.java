package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Location;
import com.example.g3bilabonnement.entity.Renter;
import com.example.g3bilabonnement.entity.helper.RenterFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Renter> searchByFilter(RenterFilter renterFilter) {
        StringBuilder sql = new StringBuilder("SELECT * FROM renter WHERE 1 = 1");

        if (renterFilter.getName() != null && !renterFilter.getName().isEmpty()) {
            sql.append(" AND (firstname LIKE '%").append(renterFilter.getName()).append("%' OR lastname LIKE '%").append(renterFilter.getName()).append("%')");
        }
        if (renterFilter.getLicensePlate() != null && !renterFilter.getLicensePlate().isEmpty()) {
            sql.append(" AND license_plate LIKE '%").append(renterFilter.getLicensePlate()).append("%'");
        }
        if (renterFilter.getPhoneNumber() != null && !renterFilter.getPhoneNumber().isEmpty()) {
            sql.append(" AND phone_number LIKE '%").append(renterFilter.getPhoneNumber()).append("%'");
        }
        if (renterFilter.getEmail() != null && !renterFilter.getEmail().isEmpty()) {
            sql.append(" AND email LIKE '%").append(renterFilter.getEmail()).append("%'");
        }

        return jdbcTemplate.query(sql.toString(), renterRowMapper);
    }
}
