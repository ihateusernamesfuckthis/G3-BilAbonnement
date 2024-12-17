package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.CarModel;
import com.example.g3bilabonnement.entity.CarModelLimit;
import com.example.g3bilabonnement.entity.helper.CarFilter;
import com.example.g3bilabonnement.entity.helper.SelectOption;
import com.example.g3bilabonnement.service.CarModelLimitService;
import com.example.g3bilabonnement.service.CarService;
import com.example.g3bilabonnement.service.SubscriptionAddonService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private HomeController homeController;
    @Autowired
    private CarService carService;
    @Autowired
    private CarModelLimitService carmodelLimitService;

    // Ensures the filter is always present in the model
    @ModelAttribute("filter")
    public CarFilter carFilter() {
        return new CarFilter();
    }

    @GetMapping("/search")
    public String searchCarsWithFilter(@ModelAttribute("filter") CarFilter carFilter, @RequestParam(required = false) boolean showSearchFilter, Model model) {
        // carFilter indeholder de værdier, som brugeren har udfyldt.
        List<Car> cars = carService.searchByFilter(carFilter);
        model.addAttribute("cars", cars);

        model.addAttribute("headerButtons", homeController.getHeaderHashMapEmpty());

        if (showSearchFilter) {
            List<String> statuses = carService.getCarStatuses();

            // Transform the list of strings into a list of SelectOption to use in formSelectFragment
            // Used for displaying the options in a dropdown
            List<SelectOption> statusSelectOptions = new ArrayList<>(); // <SelectOption>
            for (String status : statuses) {
                SelectOption option = new SelectOption(status, status);
                statusSelectOptions.add(option);
            }

            model.addAttribute("statusSelectOptions", statusSelectOptions);
        }

        model.addAttribute("showSearchFilter", showSearchFilter);
        model.addAttribute("filter", carFilter);
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

    @PostMapping("/set-limit")
    public String setLimit(@RequestParam int carModelId, @RequestParam int minLimit) {
        CarModelLimit carModelLimit = new CarModelLimit();
        CarModel carModel = new CarModel();
        carModel.setId(carModelId);
        carModelLimit.setCarModel(carModel);
        carModelLimit.setMinLimit(minLimit);
        carmodelLimitService.saveCarModelLimit(carModelLimit);

        return "redirect:/monitoring";
    }
}
