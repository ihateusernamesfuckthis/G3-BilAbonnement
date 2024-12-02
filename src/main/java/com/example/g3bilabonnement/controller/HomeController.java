package com.example.g3bilabonnement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/rentalAgreement_finalSettlementMainPage")
    public String rentalAgreementMainPage() {
        return "rentalAgreement_finalSettlementMainPage";
    }

    @GetMapping("/damageReportMainPage")
    public String damageReportMainPage() {
    return "damageReportMainPage";
}
    @GetMapping("/purchaseAgreementMainPage")
    public String purchaseAgreementMainPage() {
        return "purchaseAgreementMainPage";
    }

}

