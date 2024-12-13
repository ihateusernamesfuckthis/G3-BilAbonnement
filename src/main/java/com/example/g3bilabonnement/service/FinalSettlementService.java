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

    public void add(FinalSettlement finalSettlement) {
        finalSettlementRepository.add(finalSettlement);
    }

    public FinalSettlement getByCarId(int carId) {
        return finalSettlementRepository.getByCarId(carId);
    }

    public List<FinalSettlement> getAll() {
        // Hent alle FinalSettlements
        List<FinalSettlement> finalSettlements = finalSettlementRepository.getAll();

        // Gennemgå hver FinalSettlement
        for (FinalSettlement finalSettlement : finalSettlements) {
            // Hent RentalAgreement og sæt det
            RentalAgreement rentalAgreement = rentalAgreementService.getById(finalSettlement.getRentalAgreement().getId());
            finalSettlement.setRentalAgreement(rentalAgreement);

            // Hent DamageReport og sæt det
            DamageReport damageReport = damageReportService.getDamageReportById(finalSettlement.getDamageReport().getId());
            finalSettlement.setDamageReport(damageReport);

            // Hent Subscription med KilometerOption fra RentalAgreement
            Subscription subscription = rentalAgreement.getSubscription(); // Hent Subscription fra RentalAgreement
            if (subscription != null) {
                // Brug metoden getSubscriptionWithKilometerOption til at hente Subscription og KilometerOption
                Subscription fullSubscription = subscriptionService.getSubscriptionWithKilometerOption(subscription.getId());
                finalSettlement.getRentalAgreement().setSubscription(fullSubscription); // Sæt full Subscription på RentalAgreement
            }
        }

        return finalSettlements;
    }
}

