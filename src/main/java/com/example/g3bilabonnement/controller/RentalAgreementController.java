package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.service.RentalAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RentalAgreementController {

    @Autowired
    private RentalAgreementService rentalAgreementService;

    @PostMapping("/createRentalAgreement")
    public void createRentalAgreement() {

    }
}
