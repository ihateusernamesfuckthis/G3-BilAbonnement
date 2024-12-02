package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.helper.CarFilter;
import com.example.g3bilabonnement.service.CarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/search")
    public String searchCars(Model model) {
        return "searchCar";
    }

    @PostMapping("/search")
    public String searchCarsWithCarId(@RequestParam String carVehicleNumber,Model model) {
        CarFilter carFilter = new CarFilter("Klar til udlejning"); // Searching for available cars - how this is handled might need to change in this method
        List<Car> cars = carService.searchByVehicleNumber(carVehicleNumber, carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("currentLocation", "search");
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
