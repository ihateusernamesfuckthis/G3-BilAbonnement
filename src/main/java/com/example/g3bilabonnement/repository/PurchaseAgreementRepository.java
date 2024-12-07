package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.FinalSettlement;
import com.example.g3bilabonnement.entity.PurchaseAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseAgreementRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    CarRepository carRepository;

    public void add(PurchaseAgreement purchaseAgreement) {
        String sql = "INSERT INTO PurchaseAgreement (car_id, finalSettlement_id, " +
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
        CarRepository carRepository = new CarRepository();
        Car car = carRepository.getById(carId);
        purchaseAgreement.setCar(car);

        //sætter slutopgørelseobjektet på purchaseAgreement
        int finalSettlementId = rs.getInt("finalSettlement_id");
        FinalSettlementRepository finalSettlementRepository = new FinalSettlementRepository();
        FinalSettlement finalSettlement = finalSettlementRepository.getByCarId(finalSettlementId);
        purchaseAgreement.setFinalSettlement(finalSettlement);

        purchaseAgreement.setPickUpLocation(rs.getString("pickup_location"));
        purchaseAgreement.setFinalPrice(rs.getDouble("total_price"));

        return purchaseAgreement;
    };

    public PurchaseAgreement getCarByCarId(int carId){
        String sql = "SELECT * FROM final_settlement WHERE car_id =?";
        return jdbcTemplate.queryForObject(sql, purchaseAgreementRowMapper, carId);
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


