package com.example.g3bilabonnement.Repository;
import com.example.g3bilabonnement.Entity.Car;
import com.example.g3bilabonnement.Entity.DamageReport;
import com.example.g3bilabonnement.Entity.DamageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class DamageReportAndSpecificationRepo {
    @Autowired
    JdbcTemplate template;
    @Autowired
    CarRepo carRepo;
    private RowMapper<DamageReport> damageReportRowMapper() {
        return new RowMapper<DamageReport>() {
            @Override
            public DamageReport mapRow(ResultSet rs, int rowNum) throws SQLException {
                DamageReport damageReport = new DamageReport();
                LocalDate creationDate = rs.getDate("creation_date").toLocalDate();
                int carId = rs.getInt("carId");
                Car car = carRepo.getCarById(carId);  //Her bruger jeg CarRepo til at hente Car objektet
                damageReport.setCar(car);
                damageReport.setCreationDate(creationDate);
                return damageReport;
            }
        };
    }

    private RowMapper<DamageSpecification> damageSpecificationRowMapper() {
        return new RowMapper<DamageSpecification>() {
            @Override
            public DamageSpecification mapRow(ResultSet rs, int rowNum) throws SQLException {
                DamageSpecification damageSpecification = new DamageSpecification();
                damageSpecification.setDamageDescription(rs.getString("damage_description"));
                damageSpecification.setDamagePrice(rs.getDouble("damage_price"));
                return damageSpecification;
            }
        };
    }

    public int createDamageReport(DamageReport damageReport) {
        String damageReportSql = "INSERT INTO damage_report (creation_date, carId) VALUES (?, ?)"; //Her indsætter jeg en damagereport i databasen
        template.update(damageReportSql, damageReport.getCreationDate(), damageReport.getCar().getId());
        return getLastCreatedDamageReportId();
    }
    public int getLastCreatedDamageReportId() {
        String LastInsertIdSql = "SELECT LAST_INSERT_ID()"; //Her får jeg id´et (Det sidste autogenereret) på den nyoprettede damagereport.
        return template.queryForObject(LastInsertIdSql, Integer.class);
    }
    public void damageSpecificationsConnectedToCreatedDamageReport(List<DamageSpecification> damageSpecifications, int damageReportId){
        String damageSpecificationSql = "INSERT INTO damage_specification (damage_description, damage_price, damage_report_id) VALUES (?, ?, ?)"; //Her indsætter jeg hver damage specification der er knyttet til damagereporten
        for (DamageSpecification ds : damageSpecifications) {
            template.update(damageSpecificationSql, ds.getDamageDescription(), ds.getDamagePrice(),damageReportId);
        }
    }
    public DamageReport getDamageReportWithSpecifications(int damageReportId) {
        String getDamageReportWithSpecificationsSql = "SELECT * FROM damage_report WHERE damage_report_id = ?";
        DamageReport damageReport = template.queryForObject(getDamageReportWithSpecificationsSql, damageReportRowMapper(), damageReportId);

        String getDamageSpecificationsSql = "SELECT * FROM damage_specification WHERE damage_report_id = ?";
        List<DamageSpecification> specifications = template.query(getDamageSpecificationsSql, damageSpecificationRowMapper(), damageReportId);

        damageReport.setDamageSpecifications(specifications); // Initialiserer fielden damageSpecifications for DamageReport objektet

        return damageReport;
    }

}


