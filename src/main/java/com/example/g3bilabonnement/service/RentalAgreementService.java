package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.Location;
import com.example.g3bilabonnement.entity.RentalAgreement;
import com.example.g3bilabonnement.repository.RentalAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalAgreementService {

    @Autowired
    private RentalAgreementRepository rentalAgreementRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RenterService renterService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private CarService carService;

    public void add(RentalAgreement rentalAgreement) {
        // Add locations to database before adding the rental agreement to have the references
        int pickupLocationId = locationService.add(rentalAgreement.getPickupLocation());
        int returnLocationId = locationService.add(rentalAgreement.getReturnLocation());
        rentalAgreement.getPickupLocation().setId(pickupLocationId);
        rentalAgreement.getReturnLocation().setId(returnLocationId);

        rentalAgreementRepository.add(rentalAgreement);
        carService.updateCarStatus(rentalAgreement.getCar(), "Udlejet"); // Update car to rented out
    }

    public RentalAgreement getById(int id) {
        RentalAgreement rentalAgreement = rentalAgreementRepository.getById(id);
        rentalAgreement.setCar(carService.getById(rentalAgreement.getCar().getId()));
        rentalAgreement.setRenter(renterService.getById(rentalAgreement.getRenter().getId()));
        rentalAgreement.setSubscription(subscriptionService.getById(rentalAgreement.getSubscription().getId()));
        locationService.getById(rentalAgreement.getPickupLocation().getId());
        locationService.getById(rentalAgreement.getReturnLocation().getId());

        return rentalAgreement;
    }

    public List<RentalAgreement> getAll() {
        List<RentalAgreement> rentalAgreements = rentalAgreementRepository.getAll();
        for (RentalAgreement rentalAgreement : rentalAgreements) {
            rentalAgreement.setCar(carService.getById(rentalAgreement.getCar().getId()));
            rentalAgreement.setRenter(renterService.getById(rentalAgreement.getRenter().getId()));
            rentalAgreement.setSubscription(subscriptionService.getById(rentalAgreement.getSubscription().getId()));
            locationService.getById(rentalAgreement.getPickupLocation().getId());
            locationService.getById(rentalAgreement.getReturnLocation().getId());
        }
        return rentalAgreements;
    }

    public List<RentalAgreement> getByDateRange(LocalDate startDate, LocalDate endDate) {
        List<RentalAgreement> rentalAgreements = rentalAgreementRepository.getByDateRange(startDate, endDate);
        for (RentalAgreement rentalAgreement : rentalAgreements) {
            rentalAgreement.setCar(carService.getById(rentalAgreement.getCar().getId()));
            rentalAgreement.setRenter(renterService.getById(rentalAgreement.getRenter().getId()));
            rentalAgreement.setSubscription(subscriptionService.getById(rentalAgreement.getSubscription().getId()));
            locationService.getById(rentalAgreement.getPickupLocation().getId());
            locationService.getById(rentalAgreement.getReturnLocation().getId());
        }
        return rentalAgreements;
    }

    public double getTotalPriceByDateRange(LocalDate startDate, LocalDate endDate) {
        List<RentalAgreement> rentalAgreementsByDateRange = getByDateRange(startDate, endDate);
        double totalPrice = 0;
        for (RentalAgreement rentalAgreement : rentalAgreementsByDateRange) {
            totalPrice += rentalAgreement.getSubscription().getTotalPricePerMonth();
        }
        return totalPrice;
    }
}
