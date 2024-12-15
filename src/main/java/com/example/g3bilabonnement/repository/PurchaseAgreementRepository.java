package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.DamageReport;
import com.example.g3bilabonnement.entity.FinalSettlement;
import com.example.g3bilabonnement.entity.PurchaseAgreement;
import com.example.g3bilabonnement.entity.helper.PurchaseAgreementFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PurchaseAgreementRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(PurchaseAgreement purchaseAgreement) {
        String sql = "INSERT INTO purchase_agreement (car_id, final_settlement_id, " +
                "pickup_location, final_price) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                purchaseAgreement.getCar().getId(),
                purchaseAgreement.getFinalSettlement().getId(),
                purchaseAgreement.getPickUpLocation(),
                purchaseAgreement.getFinalPrice()
        );
    }
    private final RowMapper<PurchaseAgreement> purchaseAgreementRowMapper = (rs, rowNum) -> {
        PurchaseAgreement purchaseAgreement = new PurchaseAgreement();

        purchaseAgreement.setId(rs.getInt("id"));

        //sætter bilobjektet på købsaftalen
        int carId = rs.getInt("car_id");
        Car car = new Car();
        car.setId(carId);
        purchaseAgreement.setCar(car);


        //sætter slutopgørelseobjektet på purchaseAgreement
        int finalSettlementId = rs.getInt("final_settlement_id");
        FinalSettlement finalSettlement = new FinalSettlement();
        finalSettlement.setId(finalSettlementId);

        purchaseAgreement.setFinalSettlement(finalSettlement);

        purchaseAgreement.setPickUpLocation(rs.getString("pickup_location"));
        purchaseAgreement.setFinalPrice(rs.getDouble("final_price"));

        return purchaseAgreement;
    };

    public List<PurchaseAgreement> getAll (){
        String sql = "SELECT * FROM purchase_agreement";
        return jdbcTemplate.query(sql, purchaseAgreementRowMapper);
    }

    public List<PurchaseAgreement> searchByFilter (PurchaseAgreementFilter purchaseAgreementFilter){
        StringBuilder sql = new StringBuilder ("SELECT * FROM purchase_agreement WHERE 1 = 1");

        if (purchaseAgreementFilter.getLicensePlate() !== null && !purchaseAgreementFilter.getLicensePlate().isEmpty()){

        }

        return jdbcTemplate.query(sql.toString(), purchaseAgreementRowMapper);
    }

}


