package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.DamageReport;
import com.example.g3bilabonnement.entity.PurchaseAgreement;
import com.example.g3bilabonnement.entity.helper.PurchaseAgreementFilter;
import com.example.g3bilabonnement.repository.PurchaseAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PurchaseAgreementService {

    @Autowired
    private PurchaseAgreementRepository purchaseAgreementRepository;

    @Autowired
    private CarService carService;
    @Autowired
    private FinalSettlementService finalSettlementService;


    public void add (PurchaseAgreement purchaseAgreement) {

        if(purchaseAgreement.getCar() == null){
            throw new IllegalArgumentException(purchaseAgreement.getCar().getId() + "not found");
        }
        purchaseAgreementRepository.add(purchaseAgreement);
    }

    public List<PurchaseAgreement> searchByFilter(PurchaseAgreementFilter filter) {
        List <PurchaseAgreement> purchaseAgreements = purchaseAgreementRepository.searchByFilter(filter);

        for (PurchaseAgreement purchaseAgreement : purchaseAgreements){
            purchaseAgreement.setCar(carService.getById(purchaseAgreement.getCar().getId()));
            purchaseAgreement.setFinalSettlement(finalSettlementService.getById(purchaseAgreement.getFinalSettlement().getId()));

        }
        return purchaseAgreements;

    }

    public List <PurchaseAgreement> getAll (){
        List <PurchaseAgreement> purchaseAgreements = purchaseAgreementRepository.getAll();

        for (PurchaseAgreement purchaseAgreement : purchaseAgreements){
            purchaseAgreement.setCar(carService.getById(purchaseAgreement.getCar().getId()));
            purchaseAgreement.setFinalSettlement(finalSettlementService.getById(purchaseAgreement.getFinalSettlement().getId()));
        }
        return purchaseAgreements;
    }
    }
