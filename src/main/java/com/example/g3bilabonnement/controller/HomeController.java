package com.example.g3bilabonnement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping({"/home", "/"})
    public String home(){
        return "home";
    }



    @GetMapping("/rentalAgreement_finalSettlementMainPage")
    public String rentalAgreementMainPage(Model model) {
        return "rentalAgreementMainPage";
    }
    @GetMapping("/damageReportMainPage")
    public String damageReportMainPage() {
    return "redirect:/damageReportFunctions";
}
    @GetMapping("/purchaseStockAndMonitoringMainPage")
    public String purchaseStockAndMonitoringMainPage(Model model) {
        Map<String, String> headerButtons = new LinkedHashMap<>();
        headerButtons.put("KØBSKONTRAKT", "/purchaseAgreementFunctions");
        headerButtons.put("LAGERBEHOLDNING", "/stockFunctionsAndView");
        headerButtons.put("OVERVÅGNING", "/monitoring");

        model.addAttribute("headerButtons", headerButtons);
        return "monitoringMainPage";
    }

}

