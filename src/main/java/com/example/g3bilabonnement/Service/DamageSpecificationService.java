package com.example.g3bilabonnement.Service;

import com.example.g3bilabonnement.Entity.DamageSpecification;
import com.example.g3bilabonnement.Repository.DamageSpecificationRepo;
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
}