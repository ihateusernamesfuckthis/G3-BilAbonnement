package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.repository.DamageReportRepository;
import com.example.g3bilabonnement.repository.RentalAgreementRepository;
import com.example.g3bilabonnement.service.FinalSettlementService;
import com.example.g3bilabonnement.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@RequestMapping("/final-settlement")
@Controller
public class FinalSettlementController {

    @Autowired
    FinalSettlementService finalSettlementService;

    @Autowired
    RentalAgreementRepository rentalAgreementRepository;

    @Autowired
    DamageReportRepository damageReportRepository;

    @GetMapping("/create")
    public String createFinalSettlement(Model model){
        model.addAttribute("returnpath", "/final-settlement/create");
        return "createFinalSettlement";
    }

    @PostMapping("/create")
    public String createFinalSettlement(@RequestParam ("rentalAgreementId") int rentalAgreementId,
                                        @RequestParam ("kilometersDriven") int totalKilometersDriven){

        FinalSettlement finalSettlement = new FinalSettlement();

        //her hentes og sættes rentalagreement objektet ud fra brugerens input
        RentalAgreement rentalAgreement = rentalAgreementRepository.getById(rentalAgreementId);
        finalSettlement.setRentalAgreement(rentalAgreement);

        //Her hentes og sættes DamageReport objektet ud fra bil id'er hentet fra rental agreement objektet
        DamageReport damageReport = damageReportRepository.getDamageReportByCarId(rentalAgreement.getCar().getId());
        finalSettlement.setDamageReport(damageReport);

        //Dette er hentet fra et brugerinput
        finalSettlement.setTotalKilometerDriven(totalKilometersDriven);

        double overDrivenKilometerPrice = rentalAgreement.calculateOverdrivenKilometerPrice(totalKilometersDriven);
        finalSettlement.setOverdrivenKilometerPrice(overDrivenKilometerPrice);

        finalSettlement.calculateTotalPrice();

        finalSettlementService.add(finalSettlement);

        return "createFinalSettlementResult";
    }
}
