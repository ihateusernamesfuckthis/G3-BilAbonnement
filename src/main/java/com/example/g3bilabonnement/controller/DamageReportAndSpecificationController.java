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
import java.util.*;

@Controller
public class DamageReportAndSpecificationController {
    @Autowired
    private CarService carService;
    @Autowired
    private DamageReportService damageReportService;
    @Autowired
    private DamageSpecificationService damageSpecificationService;
    @Autowired
    HomeController homeController;

    @GetMapping("/damageReportFunctions")
    public String damageReportFunctions(Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDamageAndRepairManager());
        return "/damageAndRepairManager/searchDamageReport";
    }
    @GetMapping("/createDamageReport")
    public String damageReportFunctions(HttpSession session, Model model) {
        session.setAttribute("returnPath", "/createDamageReport");
        Car car = (Car) session.getAttribute("car");
        model.addAttribute("car", car);
        return "/damageAndRepairManager/createDamageReport";
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

        Car car = carService.getById(carId); // Jeg henter den indtastede bil
        damageReport.setCar(car); //Jeg sætter car objektet i damagereport til car
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

        return "redirect:/damageAndRepairManage/createdDamageReportView?damageReportId=" + damageReportId;
    }

    @GetMapping("/createdDamageReportView")
    public String viewDamageReport(@RequestParam int damageReportId, Model model) {
        DamageReport damageReport = damageReportService.getDamageReportById(damageReportId);
        List<DamageSpecification> damageSpecifications = damageSpecificationService.getDamageSpecificationsByReportId(damageReportId);
        damageReport.setDamageSpecifications(damageSpecifications);

        Car car = damageReport.getCar();

        model.addAttribute("damageReport", damageReport);
        model.addAttribute("car", car);

        return "/damageAndRepairManager/createdDamageReportView";
    }

    @GetMapping("/searchDamageReport")
    public String search(Model model){
    List<DamageReport> damageReports = damageReportService.getAll();

    model.addAttribute("damagereports", damageReports);

    return "/damageAndRepairManager/searchDamageReport";
    }
}