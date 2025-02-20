package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.*;
import com.example.g3bilabonnement.repository.FinalSettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinalSettlementService {

    @Autowired
    FinalSettlementRepository finalSettlementRepository;
    @Autowired
    RentalAgreementService rentalAgreementService;
    @Autowired
    DamageReportService damageReportService;
    @Autowired
    SubscriptionService subscriptionService;

    public void add(FinalSettlement finalSettlement){

        finalSettlementRepository.add(finalSettlement);
    }

    public FinalSettlement getByCarId(int carId) {
        return finalSettlementRepository.getByCarId(carId);
    }
  
    public List<FinalSettlement> getAll() {
        List<FinalSettlement> finalSettlements = finalSettlementRepository.getAll();
        for (FinalSettlement finalSettlement : finalSettlements) {
            finalSettlement.setRentalAgreement(rentalAgreementService.getById(finalSettlement.getRentalAgreement().getId()));
            finalSettlement.setDamageReport(damageReportService.getDamageReportById(finalSettlement.getDamageReport().getId()));
            finalSettlement.calculateTotalPrice();
        }
        return finalSettlements;
    }

    public FinalSettlement getById(int id){
        FinalSettlement finalSettlement = finalSettlementRepository.getById(id);
        finalSettlement.setRentalAgreement(rentalAgreementService.getById(finalSettlement.getRentalAgreement().getId()));
        finalSettlement.setDamageReport(damageReportService.getDamageReportById(finalSettlement.getDamageReport().getId()));

        double overDrivenKilometerPrice = finalSettlement.getRentalAgreement().calculateOverdrivenKilometerPrice(finalSettlement.getTotalKilometerDriven());
        finalSettlement.setOverdrivenKilometerPrice(overDrivenKilometerPrice);

        double totalPrice = finalSettlement.calculateTotalPrice();
        finalSettlement.setTotalPrice(totalPrice);
        return finalSettlementRepository.getById(id);
    }
}

