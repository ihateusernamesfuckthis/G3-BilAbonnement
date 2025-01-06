package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.DamageReport;
import com.example.g3bilabonnement.entity.helper.PurchaseAgreementFilter;
import com.example.g3bilabonnement.service.CarService;
import com.example.g3bilabonnement.service.FinalSettlementService;
import com.example.g3bilabonnement.service.PurchaseAgreementService;
import com.example.g3bilabonnement.entity.FinalSettlement;
import com.example.g3bilabonnement.entity.PurchaseAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/purchase-agreement")
@Controller
public class PurchaseAgreementController {

    @Autowired
    FinalSettlementService finalSettlementService;
    @Autowired
    CarService carService;
    @Autowired
    PurchaseAgreementService purchaseAgreementService;
    @Autowired
    HomeController homeController;

    @ModelAttribute("filter")
    PurchaseAgreementFilter purchaseAgreementFilter(){
        return new PurchaseAgreementFilter();
    }

    @GetMapping("/new")
    public String createPurchaseAgreementPage (Model model) {
        model.addAttribute("returnpath", "/purchase-agreement/create");
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());
        return "businessDeveloper/createPurchaseAgreement";
    }

    @PostMapping("/create")
    public String createPurchaseAgreement(@RequestParam("carId") int carId,
                                          @RequestParam("pickuplocation") String pickupLocation) {

        //her hentes bil-objektet til købsaftalen baseret på brugerinput
        Car car = carService.getById(carId);

        //Her hentes slutopgørelsen der sidder på det valgte bilobjekt på købsaftalen
        FinalSettlement finalSettlement = finalSettlementService.getByCarId(carId);

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
        model.addAttribute("message", "Købsaftalen er oprettet!");
        model.addAttribute("type", "success");
        model.addAttribute("redirect", "/purchase-agreement/new");
        model.addAttribute("redirectText", "Ok");

        return"businessDeveloper/createPurchaseAgreementResult";
    }

    @GetMapping("/search")
    public String searchPurchaseAgreementsWithFilter(
            @ModelAttribute("filter") PurchaseAgreementFilter filter,
            @RequestParam(required = false) boolean showSearchFilter,
            Model model) {

        // her tilføjes header knapperne
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());

        // Her hentes en liste af relevante købskontrakter baseret på filteret
        List<PurchaseAgreement> purchaseAgreements = purchaseAgreementService.searchByFilter(filter);

        model.addAttribute("purchaseAgreements", purchaseAgreements);

        model.addAttribute("showSearchFilter", showSearchFilter);
        model.addAttribute("purchaseAgreementFilter", filter);

        // Returner view-navnet, hvor data bliver præsenteret
        return "businessDeveloper/searchPurchaseAgreement";
    }

    @GetMapping("/searchPurchaseAgreement")
    public String search(Model model){
        List<PurchaseAgreement> purchaseAgreements = purchaseAgreementService.getAll();

        model.addAttribute("purchaseAgreements", purchaseAgreements);

        return "businessDeveloper/searchPurchaseAgreement";
    }
}
