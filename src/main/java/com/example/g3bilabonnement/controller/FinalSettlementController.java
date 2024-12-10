package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.service.DamageReportService;
import com.example.g3bilabonnement.service.FinalSettlementService;
import com.example.g3bilabonnement.entity.*;
import com.example.g3bilabonnement.service.RentalAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/final-settlement")
@Controller
public class FinalSettlementController {

    @Autowired
    FinalSettlementService finalSettlementService;

    @Autowired
    RentalAgreementService rentalAgreementService;

    @Autowired
    DamageReportService damageReportService;

    @GetMapping("/new")
    public String createFinalSettlement(Model model){
        model.addAttribute("returnpath", "/final-settlement/create");
        return "createFinalSettlement";
    }

    @PostMapping("/create")
    public String createFinalSettlement(@RequestParam ("rentalAgreementId") int rentalAgreementId,
                                        @RequestParam ("kilometersDriven") int totalKilometersDriven){

        FinalSettlement finalSettlement = new FinalSettlement();

        //her hentes og sættes rentalagreement objektet ud fra brugerens input
        RentalAgreement rentalAgreement = rentalAgreementService.getById(rentalAgreementId);
        finalSettlement.setRentalAgreement(rentalAgreement);

        //Her hentes og sættes DamageReport objektet ud fra bil id'er hentet fra rental agreement objektet
        DamageReport damageReport = damageReportService.getDamageReportByCarId(rentalAgreement.getCar().getId());
        finalSettlement.setDamageReport(damageReport);

        //Dette er hentet fra et brugerinput
        finalSettlement.setTotalKilometerDriven(totalKilometersDriven);

        double overDrivenKilometerPrice = rentalAgreement.calculateOverdrivenKilometerPrice(totalKilometersDriven);
        finalSettlement.setOverdrivenKilometerPrice(overDrivenKilometerPrice);

        finalSettlement.calculateTotalPrice();

        finalSettlementService.add(finalSettlement);

        return "redirect:/final-settlement/success";
    }

    @GetMapping("/success")
    public String showSuccessPage(Model model) {
        model.addAttribute("message", "Slutopgørelsen er oprettet!");
        model.addAttribute("type", "success");
        model.addAttribute("redirect", "/final-settlement/new");
        model.addAttribute("redirectText", "Ok");
        return "createFinalSettlementResult";
    }
}
