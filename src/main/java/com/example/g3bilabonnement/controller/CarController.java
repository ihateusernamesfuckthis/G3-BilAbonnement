package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.helper.CarFilter;
import com.example.g3bilabonnement.entity.helper.SelectOption;
import com.example.g3bilabonnement.service.CarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    // Ensures the filter is always present in the model
    @ModelAttribute("filter")
    public CarFilter carFilter() {
        return new CarFilter();
    }

    @GetMapping("/search")
    public String searchCarsWithFilter(@ModelAttribute("filter") CarFilter carFilter, @RequestParam(required = false) boolean showSearchFilter, Model model) {
        // Fetch filtered cars, if no filter is added uses the blank filter
        List<Car> cars = carService.searchByFilter(carFilter);

        if (showSearchFilter) {
            // TODO Replace with the status' from the database when they are added as a separate table
            List<String> statuses = List.of("Klar til udlejning", "Udlejet", "Skadet",
                    "Til reparation", "Klar til transport",
                    "Klar til salg", "Solgt", "Forhåndskøbt");

            // Transform the list of strings into a list of SelectOption to use in formSelectFragment
            // Used for displaying the options in a dropdown
            List<SelectOption> statusSelectOptions = new ArrayList<>(); // <SelectOption>
            for (String status : statuses) {
                SelectOption option = new SelectOption(status, status);
                statusSelectOptions.add(option);
            }

            model.addAttribute("statusSelectOptions", statusSelectOptions);
            model.addAttribute("filter", carFilter);
        }

        model.addAttribute("showSearchFilter", showSearchFilter);
        model.addAttribute("cars", cars);
        return "searchCar";
    }

    @PostMapping("/return-car")
    public String searchCarResults(@RequestParam int carId, HttpSession session) {
        Car car = carService.getById(carId);
        session.setAttribute("car", car);

        String returnPath = (String) session.getAttribute("returnPath");
        if (returnPath == null) {
            returnPath = "/"; // Default return path
        }

        // Redirect to the GET endpoint
        return "redirect:" + returnPath;
    }
}
