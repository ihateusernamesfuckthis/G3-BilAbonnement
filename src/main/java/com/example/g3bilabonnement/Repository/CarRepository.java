package com.example.g3bilabonnement.Repository;

import com.example.g3bilabonnement.Entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public Car findCarById(int carId) {
//        String sql = "SELECT * FROM Car WHERE id = ?";
//        try {
//            return jdbcTemplate.queryForObject(sql, new Object[]{carId}, (rs, rowNum) -> {
//                Car car = new Car();
//                car.setId(rs.getInt("id"));
//                car.setVehicleNumber(rs.getString("vehicle_number"));
//                car.setBrand(rs.getString("brand"));
//                car.setModel(rs.getString("model"));
//                car.setNetPrice(rs.getDouble("net_price"));
//                car.setStatus(rs.getString("car_status"));
//                return car;
//            });
//        } catch (EmptyResultDataAccessException e) {
//            return null; // Hvis ingen bil findes, returneres null
//        }
//    }

}
