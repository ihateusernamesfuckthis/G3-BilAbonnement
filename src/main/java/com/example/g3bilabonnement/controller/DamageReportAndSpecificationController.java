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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/damageFunctions")
    public String search(Model model) {
        List<DamageReport> damageReports = damageReportService.getAll();
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDamageAndRepairManager());
        model.addAttribute("damagereports", damageReports);

        return "damageAndRepairManager/searchDamageReport";
    }

    @GetMapping("/createDamageReport")
    public String damageReportFunctions(@RequestParam(required = false) boolean removeCar, HttpSession session, Model model) {
        session.setAttribute("returnPath", "/createDamageReport");
        if (removeCar) {
            session.removeAttribute("car");
        }
        Car car = (Car) session.getAttribute("car");
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDamageAndRepairManager());
        model.addAttribute("car", car);
        return "damageAndRepairManager/createDamageReport";
    }

    @PostMapping("/createDamageReport")
    public String createDamageReportWithSpecifications(
            @RequestParam("carId") int carId,
            @RequestParam LocalDate creationDate,
            @RequestParam List<String> damageDescriptions,
            @RequestParam List<Double> damagePrices,
            HttpSession session,
            Model model) {

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
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDamageAndRepairManager());
        session.removeAttribute("car");

        return "redirect:/viewDamageReport?damageReportId=" + damageReportId;
    }

    @GetMapping("/viewDamageReport")
    public String viewDamageReport(@RequestParam int damageReportId, Model model) {

        DamageReport damageReport = damageReportService.getDamageReportById(damageReportId);

        Car car = damageReport.getCar();
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDamageAndRepairManager());
        model.addAttribute("damageReport", damageReport);
        model.addAttribute("car", car);

        return "damageAndRepairManager/viewDamageReport";
    }

    @GetMapping("/updateDamageReport")
    public String updateDamageReport(@RequestParam int damageReportId, Model model) {
        DamageReport damageReport = damageReportService.getDamageReportById(damageReportId);
        Car car = damageReport.getCar();
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDamageAndRepairManager());
        model.addAttribute("damageReport", damageReport);
        model.addAttribute("car", car);
        return "damageAndRepairManager/updateDamageReport";
    }

    @PostMapping("/updateDamageReport")
    public String updateDamageReport(
            @RequestParam("damageReportId") int damageReportId,
            @RequestParam LocalDate creationDate,
            @RequestParam List<Integer> damageSpecificationId,
            @RequestParam List<String> damageDescriptions,
            @RequestParam List<Double> damagePrices,
            Model model) {

        DamageReport damageReport = damageReportService.getDamageReportById(damageReportId);

        damageReport.setCreationDate(creationDate);

        damageReportService.updateDamageReport(damageReport);

        List<DamageSpecification> specifications = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            DamageSpecification ds = new DamageSpecification();
            ds.setId(damageSpecificationId.get(i));
            ds.setDamageDescription(damageDescriptions.get(i));
            ds.setDamagePrice(damagePrices.get(i));
            specifications.add(ds);
        }

        damageSpecificationService.updateDamageSpecifications(specifications, damageReportId);

        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDamageAndRepairManager());

        return "redirect:/damageReportUpdateSuccess";
    }

    @GetMapping("/damageReportUpdateSuccess")
    public String showSuccessPage(Model model) {
        model.addAttribute("message", "Skaderapport er ændret");
        model.addAttribute("type", "success");
        model.addAttribute("redirect", "/damageFunctions");
        model.addAttribute("redirectText", "Ok");
        List<DamageReport> damageReports = damageReportService.getAll();
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDamageAndRepairManager());
        model.addAttribute("damagereports", damageReports);
        return "damageAndRepairManager/damageReportIsUpdated";
    }

    @GetMapping("/deleteDamageReport")
    public String deleteDamageReport(@RequestParam int damageReportId, Model model) {
        model.addAttribute("message", "Er du sikker på, at du vil slette denne skaderapport? (Slutopgørelse og købskontakt forbundet til denne skadesrapport slettes også.)");
        model.addAttribute("returnPath","/damageFunctions");
        model.addAttribute("returnAfterDelete", "/damageReportDeleted?damageReportId=" + damageReportId);
        DamageReport damageReport = damageReportService.getDamageReportById(damageReportId);
        Car car = damageReport.getCar();
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDamageAndRepairManager());
        model.addAttribute("damageReport", damageReport);
        model.addAttribute("car", car);

        return "damageAndRepairManager/deleteDamageReport";
    }

    @GetMapping("/damageReportDeleted")
    public String damageReportDeleted(@RequestParam int damageReportId, Model model) {
        boolean deleted =damageReportService.deleteDamageReportAndSpecification(damageReportId);
        if(deleted) {
            model.addAttribute("message", "Skaderapport er slettet");
            model.addAttribute("type", "success");
            model.addAttribute("redirect", "/damageFunctions");
            model.addAttribute("redirectText", "Ok");
        }else{
            model.addAttribute("message", "Der skete en fejl. Skaderapporten er ikke slettet");
            model.addAttribute("type", "failure");
            model.addAttribute("redirect", "/damageFunctions");
            model.addAttribute("redirectText", "Ok");
        }

        List<DamageReport> damageReports = damageReportService.getAll();
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDamageAndRepairManager());
        model.addAttribute("damagereports", damageReports);
        return "damageAndRepairManager/damageReportDeleted";
    }
}