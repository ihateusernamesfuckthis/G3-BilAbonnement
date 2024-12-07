package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.DamageReport;
import com.example.g3bilabonnement.entity.FinalSettlement;
import com.example.g3bilabonnement.entity.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FinalSettlementRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    RentalAgreementRepository rentalAgreementRepository;

    @Autowired
    DamageReportRepository damageReportRepository;

    private final RowMapper<FinalSettlement> finalSettlementRowMapper = (rs, rowNum) -> {
        FinalSettlement finalSettlement = new FinalSettlement();
        finalSettlement.setId(rs.getInt("id"));
        finalSettlement.setTotalKilometerDriven(rs.getInt("total_km_driven"));

        //her hentes og sættes rentalagreementet objektet ud fra rentalagreement id'et i databasen
        int rentalAgreementId = rs.getInt("rental_agreement_id");
        RentalAgreement rentalAgreement = rentalAgreementRepository.getById(rentalAgreementId);
        finalSettlement.setRentalAgreement(rentalAgreement);

        //Her hentes og sættes DamageReport objektet ud fra bil id'er hentet fra rental agreement objektet
        int carId = rentalAgreement.getCar().getId();
        DamageReport damageReport = damageReportRepository.getDamageReportByCarId(carId);
        finalSettlement.setDamageReport(damageReport);

        double overDrivenKilometerPrice = rentalAgreement.calculateOverdrivenKilometerPrice(finalSettlement.getTotalKilometerDriven());
        finalSettlement.setOverdrivenKilometerPrice(overDrivenKilometerPrice);

        double totalPrice = finalSettlement.calculateTotalPrice();
        finalSettlement.setTotalPrice(totalPrice);

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
        String sql = "SELECT * FROM final_settlement WHERE car_id =?";
        return jdbcTemplate.queryForObject(sql, finalSettlementRowMapper, carId);
    }
    }


