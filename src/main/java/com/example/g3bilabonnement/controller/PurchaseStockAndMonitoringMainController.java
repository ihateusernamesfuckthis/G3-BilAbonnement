package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.helper.FormatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class PurchaseStockAndMonitoringMainController {
    @Autowired
    com.example.g3bilabonnement.service.PurchaseAgreementService purchaseAgreementService;
    @Autowired
    com.example.g3bilabonnement.service.FinalSettlementService finalSettlementService;
    @Autowired
    com.example.g3bilabonnement.service.CarService carService;
    @Autowired
    HomeController homeController;
    @GetMapping("/purchaseAgreementFunctions")
    public String purchaseAgreementMainPage(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());
        return "/businessDeveloper/searchPurchaseAgreement";
    }
    @GetMapping("/monitoring")
    public String monitoringMainPage(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());
        return "/businessDeveloper/monitoring";
    }
    @GetMapping("/stockFunctionsAndView")
    public String stockFunctionsAndViewMainPage(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());
        model.addAttribute("totalCarPrice", FormatHelper.formatDouble(carService.getTotalCarPrice("Udlejet")));
        return "/businessDeveloper/stockFunctionsAndView";
    }
}
