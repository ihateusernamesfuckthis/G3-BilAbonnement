package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.DamageSpecification;
import com.example.g3bilabonnement.repository.DamageSpecificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageSpecificationService {

    @Autowired
    private DamageSpecificationRepo damageSpecificationRepo;

    public void createDamageSpecifications(List<DamageSpecification> damageSpecifications, int damageReportId) {
        damageSpecificationRepo.createDamageSpecifications(damageSpecifications, damageReportId);
    }

    public List<DamageSpecification> getDamageSpecificationsByReportId(int damageReportId) {
        return damageSpecificationRepo.getSpecificationsByReportId(damageReportId);
    }

    public void updateDamageSpecifications(List<DamageSpecification> damageSpecifications, int damageReportId) {
        damageSpecificationRepo.updateDamageSpecifications(damageSpecifications, damageReportId);
    }
    public boolean deleteDamageSpecification(int damageReportId) {
        return damageSpecificationRepo.deleteDamageSpecifications(damageReportId);
    }
}