package com.example.g3bilabonnement.Controller;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.FinalSettlement;
import com.example.g3bilabonnement.entity.RentalAgreement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/final-settlement")
@Controller
public class FinalSettlementController {

    @GetMapping("/create")
    public String createFinalSettlement(Model model){
        model.addAttribute("returnpath", "/final-settlement/create");
        return "createFinalSettlement";
    }

    @PostMapping("/create")
    public String createFinalSettlement(@RequestParam ("rentalAgreement_id") int rentalAgreementId,
                                        @RequestParam ("total_km_driven") int totalKilometersDriven){

        FinalSettlement finalSettlement = new FinalSettlement();

        Car car = new Car();
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setId(rentalAgreementId);
        finalSettlement.setTotalKilometerDriven(totalKilometersDriven);



    }

}
