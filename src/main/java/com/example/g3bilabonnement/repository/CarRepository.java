package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.CarModel;
import com.example.g3bilabonnement.entity.helper.CarFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
        car.setPowerSourceType(rs.getString("power_source"));
        car.setTransmissionType(rs.getString("transmission_type"));
        car.setNetPrice(rs.getDouble("net_price"));
        car.setRegistrationTax(rs.getDouble("registration_tax"));
        car.setCo2Emissions(rs.getDouble("co2_emissions"));
        car.setStatus(rs.getString("status"));
        return car;
    };

    public Car getById(int id) {
        String sql = "SELECT car.*, car_status.status, power_source.name AS power_source, transmission_type.name AS transmission_type, equipment_level.name AS equipment_level, b.name AS brand, cm.model_name as model FROM car JOIN car_status ON car.car_status_id = car_status.id LEFT JOIN power_source ON car.power_source_id = power_source.id LEFT JOIN transmission_type ON car.transmission_type_id = transmission_type.id LEFT JOIN equipment_level ON car.equipment_level_id = equipment_level.id LEFT JOIN car_model cm ON car.car_model_id = cm.id LEFT JOIN brand b ON cm.brand_id = b.id WHERE car.id = ?";
        return jdbcTemplate.queryForObject(sql, carRowMapper, id);
    }

    public List<Car> searchByFilter(CarFilter carFilter) {
        // adding 'where 1 = 1' to allow 0 -> all filters
        StringBuilder sql = new StringBuilder("SELECT car.*, car_status.status, power_source.name AS power_source, transmission_type.name AS transmission_type, equipment_level.name AS equipment_level, b.name AS brand, cm.model_name as model FROM car JOIN car_status ON car.car_status_id = car_status.id LEFT JOIN power_source ON car.power_source_id = power_source.id LEFT JOIN transmission_type ON car.transmission_type_id = transmission_type.id LEFT JOIN equipment_level ON car.equipment_level_id = equipment_level.id LEFT JOIN car_model cm ON car.car_model_id = cm.id LEFT JOIN brand b ON cm.brand_id = b.id WHERE 1 = 1");

        if (carFilter.getStatus() != null && !carFilter.getStatus().isEmpty()) {
            sql.append(" AND status = '").append(carFilter.getStatus()).append("'");
        }
        if (carFilter.getVehicleNumber() != null && !carFilter.getVehicleNumber().isEmpty()) {
            sql.append(" AND vehicle_number like '%").append(carFilter.getVehicleNumber()).append("%'");
        }
        return jdbcTemplate.query(sql.toString(), carRowMapper);
    }

    public void updateCarStatus(Car car, String newStatus) {
        String sql = "UPDATE car SET car_status_id = (SELECT car_status.id FROM car_status WHERE status = ?) WHERE car.id = ?";
        jdbcTemplate.update(sql, newStatus, car.getId());
    }

    public List<String> getCarStatuses() {
        String sql = "SELECT status FROM car_status";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<Integer> getCarIdsFromExpiredRentalAgreementsWithoutDamageReports() {
        String sql = "SELECT ra.car_id " +
                "FROM rental_agreement ra " +
                "LEFT JOIN damage_report dr ON ra.car_id = dr.car_id " +
                "WHERE ra.end_date < ? " +
                "AND dr.car_id IS NULL";
        return jdbcTemplate.queryForList(sql, Integer.class, LocalDate.now());
    }

    public double getTotalCarPrice(String carStatus) {
        String sql = "SELECT SUM(c.net_price) FROM car c \n" +
                "JOIN car_status cs ON c.car_status_id = cs.id\n" +
                "WHERE cs.status = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, carStatus);
    }
}
