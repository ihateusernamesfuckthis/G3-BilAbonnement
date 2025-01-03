package com.example.g3bilabonnement.repository;

import com.example.g3bilabonnement.entity.DamageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DamageSpecificationRepo {

    @Autowired
    private JdbcTemplate template;

    private RowMapper<DamageSpecification> damageSpecificationRowMapper() {
        return (rs, rowNum) -> {
            DamageSpecification damageSpecification = new DamageSpecification();
            damageSpecification.setId(rs.getInt("damage_specification_id"));
            damageSpecification.setDamageDescription(rs.getString("damage_description"));
            damageSpecification.setDamagePrice(rs.getDouble("damage_price"));
            return damageSpecification;
        };
    }

    public void createDamageSpecifications(List<DamageSpecification> damageSpecifications, int damageReportId) {
        String sql = "INSERT INTO damage_specification (damage_description, damage_price, damage_report_id) VALUES (?, ?, ?)";
        for (DamageSpecification ds : damageSpecifications) {
            template.update(sql, ds.getDamageDescription(), ds.getDamagePrice(), damageReportId);
        }
    }

    public List<DamageSpecification> getSpecificationsByReportId(int damageReportId) {
        String sql = "SELECT * FROM damage_specification WHERE damage_report_id = ?";
        return template.query(sql, damageSpecificationRowMapper(), damageReportId);
    }
    public void updateDamageSpecifications(List<DamageSpecification> damageSpecifications, int damageReportId) {
        for (DamageSpecification ds : damageSpecifications) {
            String sql = "UPDATE damage_specification SET damage_description = ?, damage_price = ? WHERE damage_report_id = ? AND damage_specification_id = ?";
            template.update(sql, ds.getDamageDescription(), ds.getDamagePrice(), damageReportId, ds.getId());
        }
    }
    public boolean deleteDamageSpecifications(int damageReportId) {
        String sql = "DELETE FROM damage_specification WHERE damage_report_id = ?";
        return template.update(sql,damageReportId) > 0;
    }

}