package com.example.g3bilabonnement.Service;

import com.example.g3bilabonnement.entity.RentalAgreement;
import com.example.g3bilabonnement.Repository.RentalAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.g3bilabonnement.service.CarService;

@Service
public class RentalAgreementService {

    @Autowired
    private RentalAgreementRepository rentalAgreementRepository;

    @Autowired
    private CarService carService;

    public void add(RentalAgreement rentalAgreement) {
        rentalAgreementRepository.add(rentalAgreement);
        carService.updateCarStatus(rentalAgreement.getCar(), "Udlejet");
    }
}
