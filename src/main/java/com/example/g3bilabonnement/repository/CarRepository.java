package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<Car> carRowMapper = (rs, rowNum) -> {
        Car car = new Car();
        car.setId(rs.getInt("id"));
        car.setImageUrl(rs.getString("image_url"));
        car.setVehicleNumber(rs.getString("vehicle_number"));
        car.setVinNumber(rs.getString("vin_number"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setEquipmentLevel(rs.getString("equipment_level"));
        car.setPowerSourceType(rs.getString("power_source_type"));
        car.setTransmissionType(rs.getString("transmission_type"));
        car.setNetPrice(rs.getDouble("net_price"));
        car.setRegistrationTax(rs.getDouble("registration_tax"));
        car.setCo2Emissions(rs.getDouble("co2_emissions"));
        car.setCarStatus(rs.getString("car_status"));
        return car;
    };

    public Car getById(int id) {
        String sql = "SELECT * FROM car WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, carRowMapper, id);
    }

    public List<Car> searchByVehicleNumber(String vehicleNumber) {
        String sql = "SELECT * FROM car WHERE vehicle_number like ?";
        return jdbcTemplate.query(sql, carRowMapper, '%' + vehicleNumber + '%');
    }
}
