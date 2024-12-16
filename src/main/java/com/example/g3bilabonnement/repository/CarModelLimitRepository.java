package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.CarModel;
import com.example.g3bilabonnement.entity.CarModelLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarModelLimitRepository {

     @Autowired
     JdbcTemplate jdbcTemplate;

    private final RowMapper<CarModelLimit> carModelLimitRowMapper = (rs, rowNum) -> {
        // Create CarModel object
        CarModel carModel = new CarModel();
        carModel.setBrand(rs.getString("brand"));
        carModel.setModel(rs.getString("model"));

        // Create CarModelLimit object
        CarModelLimit carModelLimit = new CarModelLimit();
        carModelLimit.setCarModel(carModel);
        carModelLimit.setMinLimit(rs.getInt("min_limit"));
        carModelLimit.setAvailableCars(rs.getInt("available_cars"));

        return carModelLimit;
    };

    public List<CarModelLimit> getCarModelLimits() {
        // Gets a list of CarModelLimit objects where the number of available cars (Klar til udlejning) is less than the minimum limit defined in the car_model_limit table
        String query = "SELECT b.name AS brand, cm.model_name as model, cl.min_limit, available_cars " +
                "FROM car_model_limit cl " +
                "JOIN car_model cm ON cl.car_model_id = cm.id " +
                "JOIN brand b ON cm.brand_id = b.id " +
                "JOIN (SELECT c.car_model_id, COUNT(*) AS available_cars " +
                "      FROM car c " +
                "      JOIN car_status cs ON c.car_status_id = cs.id " +
                "      WHERE cs.status = 'Klar til udlejning' " +
                "      GROUP BY c.car_model_id) AS available_cars_subquery " + // Groups by the car_model_id and counts the number of available cars using this group. Sets the alias for the subquery
                "ON cm.id = available_cars_subquery.car_model_id";  // Joins the subquery to the main query

        return jdbcTemplate.query(query, carModelLimitRowMapper);
    }

    public void saveCarModelLimit(CarModelLimit carModelLimit) {
        String sql = "INSERT INTO car_model_limit (car_model_id, min_limit) " +
                "VALUES (?, ?) ON DUPLICATE KEY UPDATE min_limit = VALUES(min_limit);"; // On duplicate key, update the min_limit. THis makes sure the minimum limit is updated even if the car_model_id already exists
        jdbcTemplate.update(sql, carModelLimit.getCarModel().getId(), carModelLimit.getMinLimit());
    }

}
