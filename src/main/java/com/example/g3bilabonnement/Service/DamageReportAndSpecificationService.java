package com.example.g3bilabonnement.Service;
import com.example.g3bilabonnement.Entity.Car;
import com.example.g3bilabonnement.Entity.DamageReport;
import com.example.g3bilabonnement.Repository.DamageReportAndSpecificationRepo;
import com.example.g3bilabonnement.Entity.DamageSpecification;
import com.example.g3bilabonnement.Repository.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageReportAndSpecificationService {
    @Autowired
    DamageReportAndSpecificationRepo damageReportAndSpecificationRepo;
    @Autowired
    CarRepo carRepo;
        public int createDamageReport(DamageReport damageReport) {
            return damageReportAndSpecificationRepo.createDamageReport(damageReport);// Jeg opretter en sakdesrapport
        }
        public void createDamageSpecificationsForReport(List<DamageSpecification> damageSpecifications, int damageReportId) {
            damageReportAndSpecificationRepo.damageSpecificationsConnectedToCreatedDamageReport(damageSpecifications, damageReportId);
        }

    public DamageReport getDamageReportWithSpecifications(int damageReportId) {
        return damageReportAndSpecificationRepo.getDamageReportWithSpecifications(damageReportId);
    }
        public Car getCarById(int carId) {
            return carRepo.getCarById(carId);//Her finder jeg bil på bilens id
        }
    }