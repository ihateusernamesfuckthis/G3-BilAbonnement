package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.CarModel;
import com.example.g3bilabonnement.entity.CarModelLimit;
import com.example.g3bilabonnement.entity.helper.SelectOption;
import com.example.g3bilabonnement.helper.FormatHelper;
import com.example.g3bilabonnement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class PurchaseStockAndMonitoringMainController {
    @Autowired
    PurchaseAgreementService purchaseAgreementService;
    @Autowired
    FinalSettlementService finalSettlementService;
    @Autowired
    CarService carService;
    @Autowired
    HomeController homeController;
    @Autowired
    CarModelLimitService carmodelLimitService;
    @Autowired
    CarModelService carModelService;

    @GetMapping("/purchaseAgreementFunctions")
    public String purchaseAgreementMainPage(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());
        return "/businessDeveloper/searchPurchaseAgreement";
    }
    @GetMapping("/monitoring")
    public String monitoringMainPage(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());
        return "/businessDeveloper/monitoring";
    }
    @GetMapping("/stockFunctionsAndView")
    public String stockFunctionsAndViewMainPage(@RequestParam(required = false) boolean showForm, Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());
        model.addAttribute("totalCarPrice", FormatHelper.formatDouble(carService.getTotalCarPrice("Udlejet")));

        List<CarModelLimit> carModelLimits = carmodelLimitService.getCarModelLimits();
        Collections.sort(carModelLimits); // Sorts list of CarModelLimits showing under limit first
        model.addAttribute("carModelLimits", carModelLimits);

        // Only need to get the carModelsSelectOptions if showForm is true
        if (showForm) {
            List<CarModel> carModels = carModelService.getAllCarModel();
            List<SelectOption> carModelsSelectOptions = new ArrayList<>();
            for (CarModel carModel : carModels) {
                carModelsSelectOptions.add(new SelectOption(String.valueOf(carModel.getId()), carModel.getBrand() + " " + carModel.getModel()));
            }
            model.addAttribute("carModelsSelectOptions", carModelsSelectOptions);
        }

        model.addAttribute("showForm", showForm);
        return "/businessDeveloper/stockFunctionsAndView";
    }
}
