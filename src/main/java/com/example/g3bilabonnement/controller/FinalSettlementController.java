package com.example.g3bilabonnement.Controller;

import com.example.g3bilabonnement.Repository.FinalSettlementRepository;
import com.example.g3bilabonnement.Service.FinalSettlementService;
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

    @GetMapping("/create")
    public String createFinalSettlement(Model model){
        model.addAttribute("returnpath", "/final-settlement/create");
        return "createFinalSettlement";
    }

    @PostMapping("/create")
    public String createFinalSettlement(@RequestParam ("rentalAgreementId") int rentalAgreementId,
                                        @RequestParam ("kilometersDriven") int totalKilometersDriven){


        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        // Opret RentalAgreement-objekt
        RentalAgreement rentalAgreement = new RentalAgreement();
        Subscription subscription = new Subscription();
        subscription.setAllowedKmPerMonth(500);
        rentalAgreement.setSubscription(subscription);

        FinalSettlement finalSettlement = new FinalSettlement();
        //RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setId(rentalAgreementId);
        rentalAgreement.setStartDate(startDate);
        rentalAgreement.setEndDate(endDate);


        DamageReport damageReport = new DamageReport();

        finalSettlement.setDamageReport(damageReport);
        finalSettlement.setRentalAgreement(rentalAgreement);
        finalSettlement.setTotalKilometerDriven(totalKilometersDriven);

        finalSettlementService.add(finalSettlement);

        return "createFinalSettlementResult";
    }
}
