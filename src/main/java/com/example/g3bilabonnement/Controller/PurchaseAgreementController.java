package com.example.g3bilabonnement.Controller;

import com.example.g3bilabonnement.Service.PurchaseAgreementService;
import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.DamageReport;
import com.example.g3bilabonnement.entity.FinalSettlement;
import com.example.g3bilabonnement.entity.PurchaseAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/purchase-agreement")
@Controller
public class PurchaseAgreementController {

    @Autowired
    PurchaseAgreementService purchaseAgreementService;

    @GetMapping("/create")
    public String createPurchaseAgreementPage (Model model) {
        model.addAttribute("returnpath", "/purchase-agreement/create");
        return "createPurchaseAgreement";
    }

    @PostMapping("/create")
    public String createPurchaseAgreement(@RequestParam("carId") int carId,
                                          @RequestParam("pickuplocation") String pickupLocation) {

        PurchaseAgreement purchaseAgreement = new PurchaseAgreement();
        purchaseAgreement.setPickUpLocation(pickupLocation);

        purchaseAgreement.setCar(purchaseAgreementService.findCarById(carId)); //her indsættes den specifikke bil på købskontrakten

        FinalSettlement finalSettlement = new FinalSettlement();
        finalSettlement.setId(1); // hardcoded placeholder værdi for test - hiv finalesettlement objektet fra databasen
        DamageReport placeholderDamageReport = new DamageReport(); //instantiering af placeholder damage report
        placeholderDamageReport.setId(0); // Placeholder ID
        placeholderDamageReport.setTotalDamagePrice(0.0); //Placeholder samlet pris
        finalSettlement.setDamageReport(placeholderDamageReport);
        purchaseAgreement.setFinalSettlement(finalSettlement);

        purchaseAgreementService.add(purchaseAgreement, carId);

        return "redirect:/purchase-agreement/success";
    }

    @GetMapping("/success")
    public String showSuccesPage(Model model){
        model.addAttribute("Message", "Purchase Agreement created succesfully");
        model.addAttribute("Type", "Succes");
        model.addAttribute("redirect", "/purchase-agreement/create");
        model.addAttribute("redirectText", "Ok");

        return"createPurchaseAgreementResult";
    }
}
