package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.*;
import com.example.g3bilabonnement.service.RentalAgreementService;
import com.example.g3bilabonnement.service.SubscriptionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/rental-agreement")
public class RentalAgreementController {

    @Autowired
    private RentalAgreementService rentalAgreementService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private HomeController homeController;

    @GetMapping("/new")
    public String createRentalAgreementPage(Model model, HttpSession session,
                                            @RequestParam(required = false) boolean removeRenter,
                                            @RequestParam(required = false) boolean removeCar,
                                            @RequestParam(required = false) boolean removeSubscription) {
        session.setAttribute("returnPath", "/rental-agreement/new");

        // if removeCar is true, set car to null
        if (removeCar) {
            session.removeAttribute("car");
        }
        // returns car if there is one else null - Page handles null value
        Car car = (Car) session.getAttribute("car");
        model.addAttribute("car", car);


        // if removeSubscription is true, set subscription to null
        if (removeSubscription) {
            session.removeAttribute("subscription");
        }
        // returns subscriptionId if there is one else null - Page handles null value
        Subscription subscription = (Subscription) session.getAttribute("subscription");
        model.addAttribute("subscription", subscription);


        // if removeRenter is true, set renter to null
        if (removeRenter) {
            session.removeAttribute("renter");
        }
        // returns renter if there is one else null - Page handles null value
        Renter renter = (Renter) session.getAttribute("renter");
        model.addAttribute("renter", renter);

        // Header buttons to show in the view
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDataRegistrator());

        return "dataRegistrator/createRentalAgreement";
    }

    @GetMapping("/search")
    public String searchRentalAgreements(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDataRegistrator());
        List<RentalAgreement> rentalAgreements = rentalAgreementService.getAll();
        model.addAttribute("rentalAgreements", rentalAgreements);
        return "dataRegistrator/searchRentalAgreement";
    }

    @PostMapping("/create")
    public String createRentalAgreement(@RequestParam("carId") int carId,
                                        @RequestParam("renterId") int renterId,
                                        @RequestParam("subscriptionId") int subscriptionId,
                                        @RequestParam("pickupLocationAddress") String pickupLocationAddress,
                                        @RequestParam("pickupLocationZipcode") String pickupLocationZipcode,
                                        @RequestParam("pickupLocationCity") String pickupLocationCity,
                                        @RequestParam("returnLocationAddress") String returnLocationAddress,
                                        @RequestParam("returnLocationZipcode") String returnLocationZipcode,
                                        @RequestParam("returnLocationCity") String returnLocationCity,
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

        Location pickupLocation = new Location();
        pickupLocation.setAddress(pickupLocationAddress);
        pickupLocation.setZipCode(pickupLocationZipcode);
        pickupLocation.setCity(pickupLocationCity);
        rentalAgreement.setPickupLocation(pickupLocation);

        Location returnLocation = new Location();
        returnLocation.setAddress(returnLocationAddress);
        returnLocation.setZipCode(returnLocationZipcode);
        returnLocation.setCity(returnLocationCity);
        rentalAgreement.setReturnLocation(returnLocation);

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        rentalAgreement.setStartDate(start);
        rentalAgreement.setEndDate(end);

        rentalAgreementService.add(rentalAgreement);

        // Clear session data for rental agreement to "reset" the form
        session.removeAttribute("car");
        session.removeAttribute("subscription");
        session.removeAttribute("renter");

        return "redirect:/rental-agreement/success"; // Redirect to success page
    }

    @GetMapping("/success")
    public String showSuccessPage(Model model) {
        model.addAttribute("message", "Rental agreement created successfully!");
        model.addAttribute("type", "success");
        model.addAttribute("redirect", "/rental-agreement/new");
        model.addAttribute("redirectText", "Ok");
        return "dataRegistrator/createRentalAgreementResult";
    }
}
