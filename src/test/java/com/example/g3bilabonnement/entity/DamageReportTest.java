package com.example.g3bilabonnement.entity;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DamageReportTest {


    @Test
    public void calculateTotalDamagePriceTest() {
        DamageSpecification ds1 = new DamageSpecification(1, "damageDecription1", 15000.0);
        DamageSpecification ds2 = new DamageSpecification(2, "damageDecription2", 300.0);
        DamageSpecification ds3 = new DamageSpecification(3, "damageDecription3", 0.0);
        DamageSpecification ds4 = new DamageSpecification(4, "damageDecription4", 100.0);
        DamageSpecification ds5 = new DamageSpecification(5, "damageDecription5", 500.0);
        DamageSpecification ds6 = new DamageSpecification(6, "damageDecription6", 2000.0);

        List<DamageSpecification> damageSpecifications = new ArrayList<>();
               damageSpecifications.add(ds1);
               damageSpecifications.add(ds2);
               damageSpecifications.add(ds3);
               damageSpecifications.add(ds4);
               damageSpecifications.add(ds5);
               damageSpecifications.add(ds6);

        DamageReport damageReport = new DamageReport();

        damageReport.setDamageSpecifications(damageSpecifications);

        double result= damageReport.calculateTotalDamagePrice();

        double expectedTotalDamagePrice = 15000.0 + 300.0 + 0.0 + 100.0 + 500.0 + 2000.0;

        assertEquals(expectedTotalDamagePrice , result);
    }

}
