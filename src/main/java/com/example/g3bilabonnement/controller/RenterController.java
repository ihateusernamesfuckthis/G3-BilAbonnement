package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.Renter;
import com.example.g3bilabonnement.entity.helper.CarFilter;
import com.example.g3bilabonnement.entity.helper.RenterFilter;
import com.example.g3bilabonnement.entity.helper.SelectOption;
import com.example.g3bilabonnement.service.RenterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/renter")
public class RenterController {
    @Autowired
    private RenterService renterService;

    @Autowired
    private HomeController homeController;

    // Ensures the filter is always present in the model
    @ModelAttribute("filter")
    public RenterFilter renterFilter() {
        return new RenterFilter();
    }

    @GetMapping("/search")
    public String searchCarsWithFilter(@ModelAttribute("filter") RenterFilter renterFilter, @RequestParam(required = false) boolean showSearchFilter, Model model) {
        model.addAttribute("headerButtons", homeController.getHeaderHashMapForDataRegistrator());

        // renterFilter indeholder de værdier, som brugeren har udfyldt.
        List<Renter> renters = renterService.searchByFilter(renterFilter);

        model.addAttribute("renters", renters);

        model.addAttribute("showSearchFilter", showSearchFilter);
        model.addAttribute("filter", renterFilter);
        return "/dataRegistrator/searchRenter";
    }

    @PostMapping("/return")
    public String searchCarResults(@RequestParam int renterId, HttpSession session) {
        Renter renter = renterService.getById(renterId);
        session.setAttribute("renter", renter);

        String returnPath = (String) session.getAttribute("returnPath");
        if (returnPath == null) {
            returnPath = "/"; // Default return path
        }

        // Redirect to the GET endpoint
        return "redirect:" + returnPath;
    }
}
