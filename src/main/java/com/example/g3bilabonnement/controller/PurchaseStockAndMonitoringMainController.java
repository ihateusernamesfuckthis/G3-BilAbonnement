package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.helper.FormatHelper;
import com.example.g3bilabonnement.service.CarService;
import com.example.g3bilabonnement.service.FinalSettlementService;
import com.example.g3bilabonnement.service.PurchaseAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PurchaseStockAndMonitoringMainController {
    @Autowired
    PurchaseAgreementService purchaseAgreementService;
    @Autowired
    FinalSettlementService finalSettlementService;
    @Autowired
    CarService carService;
    @Autowired
    HomeController homeController;

    @GetMapping("/purchaseAgreementFunctions")
    public String purchaseAgreementMainPage(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());
        return "redirect:/purchase-agreement/search";
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
