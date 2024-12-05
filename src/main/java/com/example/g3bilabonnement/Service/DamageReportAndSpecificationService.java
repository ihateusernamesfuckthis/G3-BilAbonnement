package com.example.g3bilabonnement.Service;

import com.example.g3bilabonnement.Entity.Car;
import com.example.g3bilabonnement.Entity.DamageReport;
import com.example.g3bilabonnement.Entity.DamageSpecification;
import com.example.g3bilabonnement.Repository.DamageReportRepo;
import com.example.g3bilabonnement.Repository.DamageSpecificationRepo;
import com.example.g3bilabonnement.Repository.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageReportAndSpecificationService {

    @Autowired
    private DamageReportRepo damageReportRepo;

    @Autowired
    private DamageSpecificationRepo damageSpecificationRepo;

    @Autowired
    private CarRepo carRepo;

    public int createDamageReport(DamageReport damageReport) {
        return damageReportRepo.createDamageReport(damageReport);
    }

    public void createDamageSpecificationsForReport(List<DamageSpecification> damageSpecifications, int damageReportId) {
        damageSpecificationRepo.createDamageSpecifications(damageSpecifications, damageReportId);
    }

    public DamageReport getDamageReportWithSpecifications(int damageReportId) {
        // Hent damage report fra DamageReportRepo
        DamageReport damageReport = damageReportRepo.getDamageReportById(damageReportId);

        // Hent bilen via CarRepo
        Car car = carRepo.getCarById(damageReport.getCar().getId());
        damageReport.setCar(car);

        // Hent damage specifications via DamageSpecificationRepo
        List<DamageSpecification> specifications = damageSpecificationRepo.getSpecificationsByReportId(damageReportId);
        damageReport.setDamageSpecifications(specifications);

        return damageReport;
    }
}