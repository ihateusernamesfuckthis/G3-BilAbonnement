package com.example.g3bilabonnement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PurchaseStockAndMonitoringMainController {
    @Autowired
    com.example.g3bilabonnement.service.PurchaseAgreementService purchaseAgreementService;
    @Autowired
    com.example.g3bilabonnement.service.FinalSettlementService finalSettlementService;
    @Autowired
    com.example.g3bilabonnement.service.CarService carService;
    @GetMapping("/purchaseAgreementFunctions")
    public String purchaseAgreementMainPage(Model model) {
        Map<String, String> headerButtons = new HashMap<>();
        headerButtons.put("KØBSKONTRAKT", "/purchaseAgreementFunctions");
        headerButtons.put("LAGERBEHOLDNING", "/stockFunctionsAndView");
        headerButtons.put("OVERVÅGNING", "/monitoring");
        model.addAttribute("headerButtons", headerButtons);
        return "purchaseAgreementMainPage";
    }
}
