package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Repository
public class DamageReportRepository {

    @Autowired
    private JdbcTemplate template;

    private RowMapper<DamageReport> damageReportRowMapper() {
        return new RowMapper<DamageReport>() {
            @Override
            public DamageReport mapRow(ResultSet rs, int rowNum) throws SQLException {
                DamageReport damageReport = new DamageReport();
                LocalDate creationDate = rs.getDate("creation_date").toLocalDate();
                int carId = rs.getInt("carId");
                Car car = new Car();// Her sætter jeg IDét i et car objekt.
                car.setId(carId);
                damageReport.setCar(car);
                damageReport.setCreationDate(creationDate);
                return damageReport;
            }
        };
    }

    public int createDamageReport(DamageReport damageReport) {
        String sql = "INSERT INTO damage_report (creation_date, carId) VALUES (?, ?)";
        template.update(sql, damageReport.getCreationDate(), damageReport.getCar().getId());
        return getLastCreatedDamageReportId();
    }

    public int getLastCreatedDamageReportId() {
        String sql = "SELECT LAST_INSERT_ID()";
        return template.queryForObject(sql, Integer.class);
    }

    public DamageReport getDamageReportById(int id) {
        String sql = "SELECT * FROM damage_report WHERE damage_report_id = ?";
        return template.queryForObject(sql, damageReportRowMapper(), id);
    }
}