package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.RentalAgreement;
import com.example.g3bilabonnement.entity.Renter;
import com.example.g3bilabonnement.entity.Subscription;
import com.example.g3bilabonnement.service.CarService;
import com.example.g3bilabonnement.service.RentalAgreementService;
import com.example.g3bilabonnement.service.RenterService;
import com.example.g3bilabonnement.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping("/rental-agreement")
public class RentalAgreementController {

    @Autowired
    private RentalAgreementService rentalAgreementService;

    @GetMapping("/create")
    public String createRentalAgreementPage(Model model) {
        model.addAttribute("returnPath", "/rental-agreement/create");
        return "createRentalAgreement";
    }

    @PostMapping("/create")
    public String createRentalAgreement( @RequestParam("carId") int carId,
                                       @RequestParam("renterId") int renterId,
                                       @RequestParam("subscriptionId") int subscriptionId,
                                       @RequestParam("startDate") String startDate,
                                       @RequestParam("endDate") String endDate) {
        RentalAgreement rentalAgreement = new RentalAgreement();

        Car car = new Car();
        car.setId(carId);
        rentalAgreement.setCar(car);
        Renter renter = new Renter();
        renter.setId(renterId);
        rentalAgreement.setRenter(renter);
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        rentalAgreement.setSubscription(subscription);
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        rentalAgreement.setStartDate(start);
        rentalAgreement.setEndDate(end);

        rentalAgreementService.add(rentalAgreement);

        return "redirect:/rental-agreement/success"; // Redirect to success page
    }

    @GetMapping("/success")
    public String showSuccessPage(Model model) {
        model.addAttribute("message", "Rental agreement created successfully!");
        model.addAttribute("type", "success");
        model.addAttribute("redirect", "/rental-agreement/create");
        model.addAttribute("redirectText", "Ok");
        return "createRentalAgreementResult";
    }
}
