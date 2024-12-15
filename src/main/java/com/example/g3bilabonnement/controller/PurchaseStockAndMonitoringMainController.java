package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.helper.DateHelper;
import com.example.g3bilabonnement.helper.FormatHelper;
import com.example.g3bilabonnement.service.CarService;
import com.example.g3bilabonnement.service.FinalSettlementService;
import com.example.g3bilabonnement.service.PurchaseAgreementService;
import com.example.g3bilabonnement.service.RentalAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
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
    public String stockFunctionsAndViewMainPage(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForBusinessDeveloper());
        model.addAttribute("totalCarPrice", FormatHelper.formatDouble(carService.getTotalCarPrice("Udlejet")));
        DateTimeFormatter danishMonthFormatter = DateTimeFormatter.ofPattern("MMMM", new Locale("da", "DK"));
        List<LocalDate[]> ranges = DateHelper.getMonthDateRanges(2, 2);
        Map<String, Double> monthToTotalPriceMap = new LinkedHashMap<>(); // Linked to keep the order
        double maxTotalPrice = 0;
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

        return "/businessDeveloper/stockFunctionsAndView";
    }
}
