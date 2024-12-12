package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.repository.FinalSettlementRepository;
import com.example.g3bilabonnement.entity.FinalSettlement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinalSettlementService {

    @Autowired
    private FinalSettlementRepository finalSettlementRepository;

    @Autowired
    private RentalAgreementService rentalAgreementService;

    @Autowired
    private DamageReportService damageReportService;

    public void add(FinalSettlement finalSettlement){
        finalSettlementRepository.add(finalSettlement);
    }

    public FinalSettlement getByCarId(int carId){
        return finalSettlementRepository.getByCarId(carId);
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
