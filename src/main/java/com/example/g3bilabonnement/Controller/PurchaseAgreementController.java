package com.example.g3bilabonnement.Controller;

import com.example.g3bilabonnement.Service.PurchaseAgreementService;
import com.example.g3bilabonnement.entity.Car;
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

        Car car = new Car();
        car.setId(carId);
        purchaseAgreement.setCar(car);
        purchaseAgreement.setPickUpLocation(pickupLocation);

        FinalSettlement finalSettlement = new FinalSettlement();
        purchaseAgreement.setFinalSettlement(finalSettlement);

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
