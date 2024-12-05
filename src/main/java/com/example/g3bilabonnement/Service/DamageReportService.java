package com.example.g3bilabonnement.Service;

import com.example.g3bilabonnement.Entity.Car;
import com.example.g3bilabonnement.Entity.DamageReport;
import com.example.g3bilabonnement.Repository.CarRepo;
import com.example.g3bilabonnement.Repository.DamageReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DamageReportService {

    @Autowired
    private DamageReportRepo damageReportRepo;
    @Autowired
    private CarRepo carRepo;


    public int createDamageReport(DamageReport damageReport) {
        return damageReportRepo.createDamageReport(damageReport);
    }

    public DamageReport getDamageReportById(int damageReportId) {
        DamageReport damageReportRepo= damageReportRepo.getDamageReportById(damageReportId);
    }
}