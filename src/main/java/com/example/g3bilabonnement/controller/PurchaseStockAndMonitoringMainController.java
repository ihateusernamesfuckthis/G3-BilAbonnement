package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.CarModel;
import com.example.g3bilabonnement.entity.CarModelLimit;
import com.example.g3bilabonnement.entity.helper.SelectOption;
import com.example.g3bilabonnement.helper.DateHelper;
import com.example.g3bilabonnement.helper.FormatHelper;
import com.example.g3bilabonnement.service.*;
import com.example.g3bilabonnement.service.CarService;
import com.example.g3bilabonnement.service.FinalSettlementService;
import com.example.g3bilabonnement.service.PurchaseAgreementService;
import com.example.g3bilabonnement.service.RentalAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

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
    RentalAgreementService rentalAgreementService;
    @Autowired
    CarModelLimitService carmodelLimitService;
    @Autowired
    CarModelService carModelService;

    @GetMapping("/purchaseAgreementFunctions")
    public String purchaseAgreementMainPage(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());
        return "redirect:/purchase-agreement/search";
    }

    @GetMapping("/monitoring")
    public String stockFunctionsAndViewMainPage(@RequestParam(required = false) boolean showForm, Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());
        model.addAttribute("totalRentedCarCount", carService.getTotalRentedCarCount());
        model.addAttribute("totalCarPrice", FormatHelper.formatDouble(carService.getTotalCarPrice("Udlejet")));

        // Car model limits
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
        // Car model limits

        // Total price per month
        DateTimeFormatter danishMonthFormatter = DateTimeFormatter.ofPattern("MMMM", new Locale("da", "DK"));
        List<LocalDate[]> ranges = DateHelper.getMonthDateRanges(2, 2);
        Map<String, Double> monthToTotalPriceMap = new LinkedHashMap<>(); // Linked to keep the order
        double maxTotalPrice = 1; // set to 1 to avoid dividing by zero if no max price is present
        for (LocalDate[] range : ranges) {
            String month = range[0].format(danishMonthFormatter);
            String capitalizedMonth = month.substring(0, 1).toUpperCase() + month.substring(1);
            double totalPrice = rentalAgreementService.getTotalPriceByDateRange(range[0], range[1]);
            monthToTotalPriceMap.put(capitalizedMonth, totalPrice);
            if (totalPrice > maxTotalPrice) {
                maxTotalPrice = totalPrice;
            }
        }
        model.addAttribute("maxTotalPrice", maxTotalPrice);
        model.addAttribute("monthToTotalPriceMap", monthToTotalPriceMap);
        // Total price per month

        return "/businessDeveloper/monitoring";
    }
}
