package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FinalSettlementRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DamageReportRepository damageReportRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;

    private final RowMapper<FinalSettlement> finalSettlementRowMapper = (rs, rowNum) -> {
        FinalSettlement finalSettlement = new FinalSettlement();
        finalSettlement.setId(rs.getInt("id"));
        finalSettlement.setTotalKilometerDriven(rs.getInt("total_km_driven"));

        //her hentes og sættes rentalagreementet objektet ud fra rentalagreement id'et i databasen
        int rentalAgreementId = rs.getInt("rental_agreement_id");
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setId(rentalAgreementId);

        finalSettlement.setRentalAgreement(rentalAgreement);

        int damageReportId = rs.getInt("damage_report_id");
        DamageReport damageReport = new DamageReport();
        damageReport.setId(damageReportId);

        finalSettlement.setDamageReport(damageReport);

        return finalSettlement;
    };

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

    public FinalSettlement getById(int id){
        String sql = "SELECT * FROM final_settlement WHERE id =?";
        return jdbcTemplate.queryForObject(sql, finalSettlementRowMapper, id);
        }

    public FinalSettlement getByCarId(int carId){

        //får bil id fra rental agreement og bruger den til at få finalsettlement
        String sql = "SELECT * FROM final_settlement as fs JOIN rental_agreement as ra ON fs.rental_agreement_id WHERE ra.car_id =?;";
        return jdbcTemplate.queryForObject(sql, finalSettlementRowMapper, carId);
    }

    public List<FinalSettlement> getAll() {
        String sql = "SELECT * FROM final_settlement";
        return jdbcTemplate.query(sql, finalSettlementRowMapper);
    }
}



