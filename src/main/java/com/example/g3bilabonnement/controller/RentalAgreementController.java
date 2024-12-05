package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.RentalAgreement;
import com.example.g3bilabonnement.entity.Renter;
import com.example.g3bilabonnement.entity.Subscription;
import com.example.g3bilabonnement.service.RentalAgreementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rental-agreement")
public class RentalAgreementController {

    @Autowired
    private RentalAgreementService rentalAgreementService;

    @GetMapping("/new")
    public String createRentalAgreementPage(Model model, HttpSession session) {
        session.setAttribute("returnPath", "/rental-agreement/new");

        // returns car if there is one else null - Page handles null value
        Car car = (Car) session.getAttribute("car");
        model.addAttribute("car", car);

        return "createRentalAgreement";
    }

    @PostMapping("/create")
    public String createRentalAgreement(@RequestParam("carId") int carId,
                                        @RequestParam("renterId") int renterId,
                                        @RequestParam("subscriptionId") int subscriptionId,
                                        @RequestParam("startDate") String startDate,
                                        @RequestParam("endDate") String endDate,
                                        HttpSession session) {
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

        // Clear session data for rental agreement to "reset" the form
        session.removeAttribute("car");

        return "redirect:/rental-agreement/success"; // Redirect to success page
    }

    @GetMapping("/success")
    public String showSuccessPage(Model model) {
        model.addAttribute("message", "Rental agreement created successfully!");
        model.addAttribute("type", "success");
        model.addAttribute("redirect", "/rental-agreement/new");
        model.addAttribute("redirectText", "Ok");
        return "createRentalAgreementResult";
    }
}
