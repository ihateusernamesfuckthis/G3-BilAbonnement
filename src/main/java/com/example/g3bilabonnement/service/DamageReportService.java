package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.DamageReport;
import com.example.g3bilabonnement.repository.CarRepository;
import com.example.g3bilabonnement.repository.DamageReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DamageReportService {

    @Autowired
    private DamageReportRepository damageReportRepository;
    @Autowired
    private CarRepository carRepository;


    public int createDamageReport(DamageReport damageReport) {
        return damageReportRepository.createDamageReport(damageReport);
    }

    public DamageReport getDamageReportById(int damageReportId) {
        DamageReport damageReport= damageReportRepository.getDamageReportById(damageReportId);
        damageReport.setCar(carRepository.getById(damageReport.getCar().getId()));
        return damageReport;
    }

    public DamageReport getDamageReportByCarId(int carID){
        DamageReport damageReport = damageReportRepository.getDamageReportByCarId(carID);
        damageReport.setCar(carRepository.getById(damageReport.getCar().getId()));
        return damageReport;
    }
}