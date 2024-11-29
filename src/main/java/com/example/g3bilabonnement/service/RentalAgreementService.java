package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.RentalAgreement;
import com.example.g3bilabonnement.repository.RentalAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalAgreementService {

    @Autowired
    private RentalAgreementRepository rentalAgreementRepository;

    public void add(RentalAgreement rentalAgreement) {
        rentalAgreementRepository.add(rentalAgreement);
    }
}
