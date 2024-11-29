package com.example.g3bilabonnement.Service;

import com.example.g3bilabonnement.Entities.Car;
import com.example.g3bilabonnement.Entities.FinalSettlement;
import com.example.g3bilabonnement.Entities.PurchaseAgreement;
import com.example.g3bilabonnement.Repository.CarRepository;
import com.example.g3bilabonnement.Repository.PurchaseAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseAgreementService {

    @Autowired
    private PurchaseAgreementRepository purchaseAgreementRepository;


    public void createPurchaseAgreement(int carId, String pickupLocation) {


        Car car = new Car();

        FinalSettlement finalSettlement = new FinalSettlement();

        PurchaseAgreement purchaseAgreement = new PurchaseAgreement(carId, pickupLocation);
        purchaseAgreement.setCar(car);
        purchaseAgreement.setFinalSettlement(finalSettlement);
        purchaseAgreement.setPickUpLocation(pickupLocation);


        purchaseAgreementRepository.createPurchaseAgreement(purchaseAgreement);
        //her ændres en bil status til sold
        purchaseAgreementRepository.updateCarStatusToSold(carId, "sold");
    }
}
