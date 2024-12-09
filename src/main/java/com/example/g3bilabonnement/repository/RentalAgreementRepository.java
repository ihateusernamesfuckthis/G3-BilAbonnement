package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RentalAgreementRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

/*private final RowMapper<RentalAgreement> rentalAgreementRowMapper = (rs, rowNum) -> {
       Car car = new Car();
        Renter renter = new Renter();
        Subscription subscription = new Subscription();
        int id = rs.getInt("id");
        car.setId(rs.getInt("car_id"));
        renter.setId(rs.getInt("renter_id"));
        subscription.setId(rs.getInt("subscription_id"));
        LocalDate startDate = rs.getDate("start_date").toLocalDate();
        LocalDate endDate = rs.getDate("end_date").toLocalDate();
       double totalPrice = rs.getDouble("total_price");

        return new RentalAgreement(id, car, subscription, renter, startDate, endDate, totalPrice);
   };
*/
//    public List<RentalAgreement> findAll() {
//        return jdbcTemplate.query("SELECT * FROM RentalAgreement", rentalAgreementRowMapper);
//    }
//
//    public RentalAgreement getById(int id) {
//        return jdbcTemplate.queryForObject("SELECT * FROM RentalAgreement WHERE id = ?", rentalAgreementRowMapper, id);
//    }

    public void add(RentalAgreement rentalAgreement) {
        String sql = "INSERT INTO rental_agreement (car_id, subscription_id, renter_id, pickup_location_id, return_location_id ,start_date, end_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                rentalAgreement.getCar().getId(),
                rentalAgreement.getSubscription().getId(),
                rentalAgreement.getRenter().getId(),
                rentalAgreement.getPickupLocation().getId(),
                rentalAgreement.getReturnLocation().getId(),
                rentalAgreement.getStartDate(),
                rentalAgreement.getEndDate());
    }

//    public void update(RentalAgreement rentalAgreement) {
//        String sql = "UPDATE RentalAgreement SET car_id = ?, subscription_id = ?, renter_id = ?, start_date = ?, end_date = ?" +
//                "WHERE id = ?";
//        jdbcTemplate.update(sql,
//                rentalAgreement.getCar().getId(),
//                rentalAgreement.getSubscription().getId(),
//                rentalAgreement.getRenter().getId(),
//                rentalAgreement.getStartDate(),
//                rentalAgreement.getEndDate(),
//                rentalAgreement.getId());
//    }
//
//    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM Artist WHERE ArtistID = ?", id);
//    }
}
