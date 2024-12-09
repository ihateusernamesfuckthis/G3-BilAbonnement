package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.PurchaseAgreement;
import com.example.g3bilabonnement.repository.PurchaseAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseAgreementService {

    @Autowired
    private PurchaseAgreementRepository purchaseAgreementRepository;


    public void add (PurchaseAgreement purchaseAgreement) {

        if(purchaseAgreement.getCar() == null){
            throw new IllegalArgumentException(purchaseAgreement.getCar().getId() + "not found");
        }

        purchaseAgreementRepository.add(purchaseAgreement);
    }
}
