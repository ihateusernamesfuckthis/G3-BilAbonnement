package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.repository.CarRepository;
import com.example.g3bilabonnement.repository.FinalSettlementRepository;
import com.example.g3bilabonnement.service.PurchaseAgreementService;
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
    @Autowired
    FinalSettlementRepository finalSettlementRepository;
    @Autowired
    CarRepository carRepository;

    @GetMapping("/create")
    public String createPurchaseAgreementPage (Model model) {
        model.addAttribute("returnpath", "/purchase-agreement/create");
        return "createPurchaseAgreement";
    }

    @PostMapping("/create")
    public String createPurchaseAgreement(@RequestParam("carId") int carId,
                                          @RequestParam("pickuplocation") String pickupLocation) {

        //her hentes bil-objektet til købsaftalen baseret på brugerinput
        Car car = carRepository.getById(carId);

        //Her hentes slutopgørelsen der sidder på det valgte bilobjekt på købsaftalen
        FinalSettlement finalSettlement = finalSettlementRepository.getByCarId(carId);

        // her oprettes købsaftalen og pickupLocation sættes på købsaftale-objeketet
        PurchaseAgreement purchaseAgreement = new PurchaseAgreement();

        purchaseAgreement.setCar(car);
        purchaseAgreement.setFinalSettlement(finalSettlement);
        purchaseAgreement.setPickUpLocation(pickupLocation);
        purchaseAgreement.setFinalPrice(purchaseAgreement.calculateFinalPrice());

        purchaseAgreementService.add(purchaseAgreement);

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