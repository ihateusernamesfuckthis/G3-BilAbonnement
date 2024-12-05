package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.PurchaseAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseAgreementRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(PurchaseAgreement purchaseAgreement) {
        String sql = "INSERT INTO PurchaseAgreement (car_id, finalSettlement_id, " +
                "pickup_location, final_price) VALUES (?, ?, ?, ?)";


        // Debugging for at tjekke værdier
        System.out.println("Car ID: " + purchaseAgreement.getCar().getId());
        System.out.println("Final Settlement ID: " + purchaseAgreement.getFinalSettlement().getId());
        System.out.println("PickUp Location: " + purchaseAgreement.getPickUpLocation());
        System.out.println("Final Price: " + purchaseAgreement.getFinalPrice());


        jdbcTemplate.update(sql,
                purchaseAgreement.getCar().getId(),
                purchaseAgreement.getFinalSettlement().getId(),
                purchaseAgreement.getPickUpLocation(),
                purchaseAgreement.getCar().getNetPrice()-purchaseAgreement.getCar().getDamageReport().getTotalDamagePrice()
        );
    }

    public Car findCarById(int carId) {
        String sql = "SELECT * FROM Car WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{carId}, (rs, rowNum) -> {
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
            car.setStatus(rs.getString("car_status"));
            return car;
        });
    }


    public void updateCarStatusToPrepurchased(int carId, Boolean prepurchased) {
        String sql = "UPDATE Car SET prepurchased = ? WHERE id = ?";
        jdbcTemplate.update(sql, prepurchased, carId);
    }

    public boolean hasDamageReport(int carId) {
        String sql = "SELECT COUNT(*) FROM DamageReport WHERE car_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{carId}, Integer.class);
        return count != null && count > 0;
    }
}


