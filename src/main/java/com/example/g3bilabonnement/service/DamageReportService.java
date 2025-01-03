package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.DamageReport;
import com.example.g3bilabonnement.entity.DamageSpecification;
import com.example.g3bilabonnement.repository.DamageReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageReportService {

    @Autowired
    private DamageReportRepository damageReportRepository;
    @Autowired
    private DamageSpecificationService damageSpecificationService;
    @Autowired
    private CarService carService;


    public int createDamageReport(DamageReport damageReport) {
        return damageReportRepository.createDamageReport(damageReport);
    }

    public DamageReport getDamageReportById(int damageReportId) {
        DamageReport damageReport= damageReportRepository.getDamageReportById(damageReportId);
        damageReport.setCar(carService.getById(damageReport.getCar().getId()));
        List<DamageSpecification> damageSpecifications = damageSpecificationService.getDamageSpecificationsByReportId(damageReport.getId());
        damageReport.setDamageSpecifications(damageSpecifications);
        damageReport.calculateTotalDamagePrice();
        return damageReport;
    }

    public DamageReport getDamageReportByCarId(int carID){
        DamageReport damageReport = damageReportRepository.getDamageReportByCarId(carID);
        damageReport.setCar(carService.getById(damageReport.getCar().getId()));
        List<DamageSpecification> damageSpecifications = damageSpecificationService.getDamageSpecificationsByReportId(damageReport.getId());
        damageReport.setDamageSpecifications(damageSpecifications);
        damageReport.calculateTotalDamagePrice();
        return damageReport;
    }

    public List <DamageReport> getAll (){
        List <DamageReport> damageReports = damageReportRepository.getAll();

        for (DamageReport damageReport : damageReports){
            damageReport.setCar(carService.getById(damageReport.getCar().getId()));
            List<DamageSpecification> damageSpecifications = damageSpecificationService.getDamageSpecificationsByReportId(damageReport.getId());
            damageReport.setDamageSpecifications(damageSpecifications);
            damageReport.calculateTotalDamagePrice();
        }
        return damageReports;
    }
    public void updateDamageReport(DamageReport damageReport) {
        damageReportRepository.updateDamageReport(damageReport);
    }
    public boolean deleteDamageReportAndSpecification(int id) {
        boolean damageSpecificationDeleted = damageSpecificationService.deleteDamageSpecification(id);
        boolean damageReportDeleted = damageReportRepository.deleteDamageReport(id);
        boolean damageReportAndSpecificationsDeleted = damageReportDeleted && damageSpecificationDeleted;
        return damageReportAndSpecificationsDeleted ;
    }
}