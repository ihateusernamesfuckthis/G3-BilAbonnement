package com.example.g3bilabonnement.Repository;
import com.example.g3bilabonnement.Entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CarRepo {
    @Autowired
    private JdbcTemplate template;

    private RowMapper<Car> getCarWithRowMapper() {
        return new RowMapper<Car>() {
            @Override
            public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
                Car car = new Car(); //Jeg laver et nyt car objekt og efterfølgende adder jeg dataen fra hver kolonne til objektets fields.
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
                car.setCarStatus(rs.getString("status"));// car_status fra car_status tabellen

                return car;
            }
        };
    }
    public Car getCarById(int id) {
        String getCarByIdsql="""
        SELECT car.*, car_status.status
        FROM car
        JOIN car_status ON car.car_status_id = car_status.id
        WHERE car.id = ?;
        """;
        return template.queryForObject(getCarByIdsql, new Object[]{id}, getCarWithRowMapper());
    }
}