package com.example.g3bilabonnement.Repository;

import com.example.g3bilabonnement.entity.FinalSettlement;
import com.example.g3bilabonnement.entity.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FinalSettlementRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add (FinalSettlement finalSettlement){
        String sql = "INSERT INTO final_settlement (rental_agreement_id, damage_report_id, total_km_driven, total_price) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                finalSettlement.getRentalAgreement().getId(),
                finalSettlement.getDamageReport().getId(),
                finalSettlement.getTotalKilometerDriven(),
                finalSettlement.getTotalPrice()
                );
    }

    public RentalAgreement findRentalAgreementById(int rentalAgreementId){
        String sql = "SELECT * FROM rental_agreement WHERE id =?";
        return jdbcTemplate.queryForObject(sql, new Object[]{rentalAgreementId}, (rs, rowNum) -> {
           RentalAgreement rentalAgreement = new RentalAgreement();
           rentalAgreement.setId(rs.getInt("id"));

           return rentalAgreement;
        });

    }

}
