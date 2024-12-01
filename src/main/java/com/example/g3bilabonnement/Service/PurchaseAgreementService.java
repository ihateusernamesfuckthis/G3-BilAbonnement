package com.example.g3bilabonnement.Service;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.FinalSettlement;
import com.example.g3bilabonnement.entity.PurchaseAgreement;
import com.example.g3bilabonnement.Repository.PurchaseAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseAgreementService {

    @Autowired
    private PurchaseAgreementRepository purchaseAgreementRepository;


    public void add (PurchaseAgreement purchaseAgreement) {


        purchaseAgreementRepository.add(purchaseAgreement);

        purchaseAgreementRepository.updateCarStatusToSold(purchaseAgreement.getCar().getId(), "sold");
    }
}
