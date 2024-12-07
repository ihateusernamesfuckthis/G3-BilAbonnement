package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.DamageReport;
import com.example.g3bilabonnement.repository.CarRepository;
import com.example.g3bilabonnement.repository.DamageReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DamageReportService {

    @Autowired
    private DamageReportRepo damageReportRepo;
    @Autowired
    private CarRepository carRepository;


    public int createDamageReport(DamageReport damageReport) {
        return damageReportRepo.createDamageReport(damageReport);
    }

    public DamageReport getDamageReportById(int damageReportId) {
        DamageReport damageReport= damageReportRepo.getDamageReportById(damageReportId);
        damageReport.setCar(carRepository.getById(damageReport.getCar().getId()));
        return damageReport;
    }
}