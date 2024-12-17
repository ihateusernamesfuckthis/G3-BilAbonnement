package com.example.g3bilabonnement.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Map<String,String> getHeaderHashMapForDataRegistrator(){
        Map<String, String> headerButtons = new LinkedHashMap<>();
        headerButtons.put("LEJEAFTALE", "/rentalAgreementFunctions");
        headerButtons.put("SLUTOPGØRELSE", "/finalSettlementFunctions");
        return headerButtons;
    }
    public Map<String,String> getHeaderHashMapForDamageAndRepairManager(){
        Map<String, String> headerButtons = new LinkedHashMap<>();
        headerButtons.put("SKADERAPPORT", "/damageFunctions");
        return headerButtons;
    }
    public Map<String,String> getHeaderHashMapForBusinessDeveloper(){
        Map<String, String> headerButtons = new LinkedHashMap<>();
        headerButtons.put("KØBSKONTRAKT", "/purchaseAgreementFunctions");
        headerButtons.put("OVERVÅGNING", "/monitoring");
        return headerButtons;
    }
    @GetMapping("/rentalAgreementPage")
    public String rentalAgreementMainPage() {
        return "redirect:rentalAgreementFunctions";
    }
    @GetMapping("/damageReportPage")
    public String damageReportMainPage() {
    return "redirect:/damageFunctions";
}
    @GetMapping("/monitoringPage")
    public String purchaseStockAndMonitoringMainPage() {
        return "redirect:/monitoring";
    }

}

