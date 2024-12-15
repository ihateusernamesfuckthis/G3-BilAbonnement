package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.FinalSettlement;
import com.example.g3bilabonnement.entity.RentalAgreement;
import com.example.g3bilabonnement.service.FinalSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RentalAgreementAndFinalSettlementMainController {
    @Autowired
    HomeController homeController;
    @Autowired
    FinalSettlementService finalSettlementService;
    @GetMapping("/rentalAgreementFunctions")
    public String rentalAgreementFunctions(Model model) {
        return "redirect:/rental-agreement/search";
    }

    @GetMapping("/finalSettlementFunctions")
    public String finalSettlementFunctions(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDataRegistrator());
        List<FinalSettlement> finalSettlements = finalSettlementService.getAll();
        model.addAttribute("finalSettlements", finalSettlements);
        return "/dataRegistrator/searchFinalSettlement";
    }
}
