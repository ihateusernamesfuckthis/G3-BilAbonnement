package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.DamageReport;
import com.example.g3bilabonnement.entity.PurchaseAgreement;
import com.example.g3bilabonnement.repository.PurchaseAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseAgreementService {

    @Autowired
    private PurchaseAgreementRepository purchaseAgreementRepository;

    public Car findCarById(int carId){
        return purchaseAgreementRepository.findCarById(carId);
    }


    public void add (PurchaseAgreement purchaseAgreement, int carId) {


        if(purchaseAgreement.getCar() == null){
            throw new IllegalArgumentException(purchaseAgreement.getCar().getId() + "not found");
        }

        if (purchaseAgreement.getCar().getDamageReport() == null) { //placeholder damagereport
            DamageReport placeholderDamageReport = new DamageReport();
            placeholderDamageReport.setId(0); // Indikerer, at det er en placeholder
            placeholderDamageReport.setTotalDamagePrice(1000.0); // Ingen skade
            purchaseAgreement.getCar().setDamageReport(placeholderDamageReport);
        }


        purchaseAgreementRepository.add(purchaseAgreement);

    }
}
