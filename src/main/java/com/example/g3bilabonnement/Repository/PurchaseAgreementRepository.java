package com.example.g3bilabonnement.Repository;

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
                purchaseAgreement.getFinalPrice()
        );
    }

    public void updateCarStatusToSold(int carId, String car_status) {
        String sql = "UPDATE Car SET car_status = ? WHERE id = ?";
        jdbcTemplate.update(sql, car_status, carId);
    }
}
