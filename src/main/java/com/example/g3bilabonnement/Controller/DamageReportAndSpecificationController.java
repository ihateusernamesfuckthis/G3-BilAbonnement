package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.DamageReport;
import com.example.g3bilabonnement.entity.DamageSpecification;
import com.example.g3bilabonnement.service.CarService;
import com.example.g3bilabonnement.service.DamageReportService;
import com.example.g3bilabonnement.service.DamageSpecificationService;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/damageReportFunctions")
    public String updateContractSection(@RequestParam String damageReportFunction,HttpSession session, Model model) {
        model.addAttribute("selectedContractFunction", damageReportFunction);
        session.setAttribute("returnPath", "/damageReportFunctions?damageReportFunction=create");
        Car car = (Car) session.getAttribute("car");
        model.addAttribute("car", car);
        return "damageReportMainPage";
    }

    @PostMapping("/createDamageReport")
    public String createDamageReportWithSpecifications(
            @RequestParam("carId") int carId,
            @RequestParam LocalDate creationDate,
            @RequestParam List<String> damageDescriptions,
            @RequestParam List<Double> damagePrices,
            HttpSession session,
            Model model) {

        /*List<Integer> validCarIds = carService.getCarIdsFromExpiredRentalAgreementsWithoutDamageReports();

        if (!validCarIds.contains(carId)) {
            model.addAttribute("errorMessage", "Skaderapport kan kun oprettes, hvis lejeaftalen er udløbet og bilen ikke har en eksisterende skaderapport.");
            model.addAttribute("errorType", "failure");
            return "damageReportMainPage";
        }*/

        DamageReport damageReport = new DamageReport();

        Car car = new Car();
        car.setId(carId);
        damageReport.setCar(car);
        damageReport.setCar(car);
        damageReport.setCreationDate(creationDate);
        int damageReportId = damageReportService.createDamageReport(damageReport);

        List<DamageSpecification> specifications = new ArrayList<>();
        for (int i = 0; i < damageDescriptions.size(); i++) {
            DamageSpecification ds = new DamageSpecification();
            ds.setDamageDescription(damageDescriptions.get(i));
            ds.setDamagePrice(damagePrices.get(i));
            specifications.add(ds);
        }
        damageSpecificationService.createDamageSpecifications(specifications, damageReportId);

        session.removeAttribute("car");

        return "redirect:/createdDamageReportView?damageReportId=" + damageReportId;
    }

    @GetMapping("/createdDamageReportView")
    public String viewDamageReport(@RequestParam int damageReportId, Model model) {
        DamageReport damageReport = damageReportService.getDamageReportById(damageReportId);
        List<DamageSpecification> damageSpecifications = damageSpecificationService.getDamageSpecificationsByReportId(damageReportId);
        damageReport.setDamageSpecifications(damageSpecifications);

        Car car = damageReport.getCar();

        model.addAttribute("damageReport", damageReport);
        model.addAttribute("car", car);

        return "createdDamageReportView";
    }
}