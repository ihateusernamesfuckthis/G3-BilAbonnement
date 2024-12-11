package com.example.g3bilabonnement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping({"/home", "/"})
    public String home(){
        return "home";
    }

    @GetMapping("/rentalAgreement_finalSettlementMainPage")
    public String rentalAgreementMainPage(Model model) {
        Map<String, String> headerButtons = new HashMap<>();
        headerButtons.put("LEJEAFTALE", "/rentalAgreementFunctions");
        headerButtons.put("SLUTOPGØRELSE", "/finalSettlementFunctions");
        model.addAttribute("headerButtons", headerButtons);
        return "rentalAgreement_finalSettlementMainPage";
    }

    @GetMapping("/damageReportMainPage")
    public String damageReportMainPage() {
    return "redirect:/damageReportFunctions";
}
    @GetMapping("/purchaseAgreementMainPage")
    public String purchaseAgreementMainPage() {
        return "purchaseAgreementMainPage";
    }

}

