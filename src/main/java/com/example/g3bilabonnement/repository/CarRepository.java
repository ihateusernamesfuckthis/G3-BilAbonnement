package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.helper.CarFilter;
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
        System.out.println("Executing SQL: " + sql + " with id = " + id);
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, carRowMapper);
    }

    public List<Car> searchByVehicleNumber(String vehicleNumber, CarFilter carFilter) {
        String sql = "SELECT car.*, car_status.status FROM car JOIN car_status ON car.car_status_id = car_status.id WHERE vehicle_number like ?";

        if (!carFilter.status.isEmpty()) {
            // Only include filter options if any are set
            sql += " AND car_status.status = '" + carFilter.status + "'";
        }
        return jdbcTemplate.query(sql, carRowMapper, '%' + vehicleNumber + '%');
    }

    public void updateCarStatus(Car car, String newStatus){
        String sql = "UPDATE car SET car_status_id = (SELECT car_status.id FROM car_status WHERE status = ?) WHERE car.id = ?";
        jdbcTemplate.update(sql, newStatus, car.getId());
    }
}
