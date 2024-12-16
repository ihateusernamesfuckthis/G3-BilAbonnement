package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.CarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarModelRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<CarModel> carModelRowMapper = (rs, rowNum) -> {
        CarModel carModel = new CarModel();
        carModel.setId(rs.getInt("id"));
        carModel.setModel(rs.getString("model_name"));
        carModel.setBrand(rs.getString("brand_name"));
        return carModel;
    };

    public List<CarModel> getAllCarModel(){
        String sql = "SELECT cm.id, cm.model_name, b.name AS brand_name FROM car_model cm JOIN brand b ON cm.brand_id = b.id";
        return jdbcTemplate.query(sql, carModelRowMapper);
    }
}
