package com.example.g3bilabonnement.Repository;

import com.example.g3bilabonnement.Entities.PurchaseAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseAgreementRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createPurchaseAgreement(PurchaseAgreement purchaseAgreement) {
        String sql = "INSERT INTO PurchaseAgreement (car, finalSettlementId, pickupLocation, finalPrice) VALUES (?, ?, ?, ?)";
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
