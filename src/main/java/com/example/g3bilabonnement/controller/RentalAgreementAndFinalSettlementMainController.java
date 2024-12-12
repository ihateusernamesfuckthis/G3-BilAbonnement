package com.example.g3bilabonnement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class RentalAgreementAndFinalSettlementMainController {
    @Autowired
    HomeController homeController;

    @GetMapping("/rentalAgreementFunctions")
    public String rentalAgreementFunctions(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDataRegistrator());
        return "/dataRegistrator/searchRentalAgreement";
    }

    @GetMapping("/finalSettlementFunctions")
    public String finalSettlementFunctions(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDataRegistrator());
        return "/dataRegistrator/searchFinalSettlement";
    }
}
