package com.example.g3bilabonnement.Repository;

import com.example.g3bilabonnement.entity.FinalSettlement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FinalSettlementRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add (FinalSettlement finalSettlement){
        String sql = "INSERT INTO final_settlement (rental_agreement_id, car_id, damage_costs, overdriven_km_price, total_price) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                finalSettlement.getRentalAgreement().getId(),
                finalSettlement.getCar().getId(),
                finalSettlement.getTotalDamageReportPrice(),
                finalSettlement.getTotalOverdrivenKilometerPrice(),
                finalSettlement.getTotalPrice()
                );
    }
}
