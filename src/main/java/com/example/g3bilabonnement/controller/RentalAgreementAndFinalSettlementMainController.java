package com.example.g3bilabonnement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class RentalAgreementAndFinalSettlementMainController {
    @GetMapping("/rentalAgreementFunctions")
    public String rentalAgreementFunctions(Model model) {
        Map<String, String> headerButtons = new LinkedHashMap<>();
        headerButtons.put("LEJEAFTALE", "/rentalAgreementFunctions");
        headerButtons.put("SLUTOPGØRELSE", "/finalSettlementFunctions");
        model.addAttribute("headerButtons", headerButtons);
        return "rentalAgreementMainPage";
    }

    @GetMapping("/finalSettlementFunctions")
    public String finalSettlementFunctions(Model model) {
        Map<String, String> headerButtons = new LinkedHashMap<>();
        headerButtons.put("LEJEAFTALE", "/rentalAgreementFunctions");
        headerButtons.put("SLUTOPGØRELSE", "/finalSettlementFunctions");
        model.addAttribute("headerButtons", headerButtons);
        return "finalSettlementMainPage";
    }
    }
