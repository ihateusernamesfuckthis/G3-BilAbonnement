package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.RentalAgreement;
import com.example.g3bilabonnement.repository.LocationRepository;
import com.example.g3bilabonnement.entity.Subscription;
import com.example.g3bilabonnement.repository.RentalAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalAgreementService {

    @Autowired
    private RentalAgreementRepository rentalAgreementRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private RenterService renterService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private CarService carService;

    public void add(RentalAgreement rentalAgreement) {
        // Add locations to database before adding the rental agreement to have the references
        int pickupLocationId = locationRepository.add(rentalAgreement.getPickupLocation());
        int returnLocationId = locationRepository.add(rentalAgreement.getReturnLocation());
        rentalAgreement.getPickupLocation().setId(pickupLocationId);
        rentalAgreement.getReturnLocation().setId(returnLocationId);

        rentalAgreementRepository.add(rentalAgreement);
        carService.updateCarStatus(rentalAgreement.getCar(), "Udlejet"); // Update car to rented out
    }

    public RentalAgreement getById(int id){
        RentalAgreement rentalAgreement = rentalAgreementRepository.getById(id);

        rentalAgreement.setCar(carService.getById(rentalAgreement.getCar().getId()));
        rentalAgreement.setRenter(renterService.getById(rentalAgreement.getRenter().getId()));
        rentalAgreement.setSubscription(subscriptionService.getById(rentalAgreement.getSubscription().getId()));

        return rentalAgreement;
    }
}
