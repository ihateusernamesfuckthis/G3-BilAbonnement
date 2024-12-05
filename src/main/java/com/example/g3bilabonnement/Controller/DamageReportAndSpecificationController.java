package com.example.g3bilabonnement.Controller;

import com.example.g3bilabonnement.Entity.Car;
import com.example.g3bilabonnement.Entity.DamageReport;
import com.example.g3bilabonnement.Entity.DamageSpecification;
import com.example.g3bilabonnement.Service.CarService;
import com.example.g3bilabonnement.Service.DamageReportService;
import com.example.g3bilabonnement.Service.DamageSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DamageReportAndSpecificationController {
    @Autowired
    private CarService carService;
    @Autowired
    private DamageReportService damageReportService;
    @Autowired
    private DamageSpecificationService damageSpecificationService;
    @GetMapping("/damageReportMainPage")
    public String damageReportMainPage() {
        //Her kan biler evt. listes?
        //Her kan skadesrapporter evt. listet?
        return "damageReportMainPage";
    }

    @GetMapping("/damageReportFunctions")
    public String updateContractSection(@RequestParam String damageReportFunction, Model model) {
        model.addAttribute("selectedContractFunction", damageReportFunction);
        return "damageReportMainPage";
    }

   @PostMapping("/createDamageReport")
    public String createDamageReportWithSpecifications(
            @RequestParam int carId,
            @RequestParam LocalDate creationDate,
            @RequestParam List<String> damageDescriptions,
            @RequestParam List<Double> damagePrices) {

        DamageReport damageReport = new DamageReport();
        Car car = carService.getCarById(carId); // Jeg henter den indtastede bil
        damageReport.setCar(car); //Jeg sætter car objektet i damagereport til car
        damageReport.setCreationDate(creationDate);
        int damageReportId = damageReportService.createDamageReport(damageReport); //Her gemmes skaderapporten og id til den gemt skadesrapport returneres.

        List<DamageSpecification> specifications = new ArrayList<>();// Her opretter jeg en liste med specifikationer
        for (int i = 0; i < damageDescriptions.size(); i++) {
            DamageSpecification ds = new DamageSpecification();
            ds.setDamageDescription(damageDescriptions.get(i));
            ds.setDamagePrice(damagePrices.get(i));
            specifications.add(ds);
        }
        damageSpecificationService.createDamageSpecifications(specifications, damageReportId);
        return "redirect:/createdDamageReportView?damageReportId=" + damageReportId;
    }

    @GetMapping("/createdDamageReportView")
    public String viewDamageReport(@RequestParam int damageReportId, Model model) {
            DamageReport damageReport = damageReportService.getDamageReportById(damageReportId);

            List<DamageSpecification> damageSpecifications = damageSpecificationService.getDamageSpecificationsByReportId(damageReportId);
            damageReport.setDamageSpecifications(damageSpecifications);

            model.addAttribute("damageReport", damageReport);
            return "createdDamageReportView";
        }
    }