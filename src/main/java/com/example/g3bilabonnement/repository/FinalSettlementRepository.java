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
    RentalAgreementRepository rentalAgreementRepository;

    @Autowired
    DamageReportRepository damageReportRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;


    private final RowMapper<FinalSettlement> finalSettlementRowMapper = (rs, rowNum) -> {
        FinalSettlement finalSettlement = new FinalSettlement();

        // Hent data fra resultatsættet og sæt værdierne
        finalSettlement.setId(rs.getInt("id"));
        finalSettlement.setTotalKilometerDriven(rs.getInt("total_km_driven"));
        finalSettlement.setTotalPrice(rs.getDouble("total_price"));

        // Hent og sæt relateret RentalAgreement
        int rentalAgreementId = rs.getInt("rental_agreement_id");
        RentalAgreement rentalAgreement = rentalAgreementRepository.getById(rentalAgreementId);

        // Hent Subscription fra RentalAgreement
        int subscriptionId = rentalAgreement.getSubscription().getId(); // Hent Subscription fra RentalAgreement
        Subscription subscription = subscriptionRepository.getById(subscriptionId);

        // Hent KilometerOption og sæt den korrekt
        if (subscription != null) {
            int kilometerOptionsId = subscription.kilometerOptions.getId(); // Kilometers options ID fra Subscription
            KilometerOption kilometerOption = kilometerOptionRepository.getById(kilometerOptionsId); // Hent KilometerOption
            subscription.setKilometerOption(kilometerOption); // Sæt KilometerOption
        }

        finalSettlement.setRentalAgreement(rentalAgreement);

        // Hent og sæt relateret DamageReport
        int damageReportId = rs.getInt("damage_report_id");
        DamageReport damageReport = damageReportRepository.getDamageReportById(damageReportId);
        finalSettlement.setDamageReport(damageReport);

        // Beregn og sæt overdrivelse af kilometerpris
        double overDrivenKilometerPrice = rentalAgreement.calculateOverdrivenKilometerPrice(finalSettlement.getTotalKilometerDriven());
        finalSettlement.setOverdrivenKilometerPrice(overDrivenKilometerPrice);

        // Beregn totalprisen for final settlement
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

        //får bil id fra rental agreement og bruger den til at få finalsettlement
        String sql = "SELECT * FROM final_settlement as fs JOIN rental_agreement as ra ON fs.rental_agreement_id WHERE ra.car_id =?;";
        return jdbcTemplate.queryForObject(sql, finalSettlementRowMapper, carId);
    }
    public List<FinalSettlement> getAll() {
        String sql = "SELECT id, rental_agreement_id, damage_report_id, total_km_driven, total_price " +
                "FROM final_settlement";

        List<FinalSettlement> finalSettlements = jdbcTemplate.query(sql, finalSettlementRowMapper);

        return finalSettlements;
    }

    }


