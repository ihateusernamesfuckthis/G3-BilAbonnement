package Repository;
import Entity.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.Date;
    @Repository
    public class DamageSpecificationRepo {
        @Autowired
        JdbcTemplate template;

        public void createDamageReport(DamageReport damageReport) {
            String sql = "INSERT INTO damage_report (carId, creation_date) VALUES (?, ?)";
            template.update(sql, damageReport.getCar(), damageReport.getCreationDate(), new Date(damageReport.getCreationDate(()));
        }
}
