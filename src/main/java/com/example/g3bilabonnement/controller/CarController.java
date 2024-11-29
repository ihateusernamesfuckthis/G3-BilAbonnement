package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.Car;
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


@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/search")
    public String searchCars(@RequestParam String returnPath, Model model) {
        model.addAttribute("returnPath", returnPath);
        return "searchCar";
    }

    //TODO What happens if this is called without return path @RequestParam(required = false) String returnPath
    @PostMapping("/search-car")
    public String searchCarResults(@RequestParam String returnPath, @RequestParam int carId, HttpSession session) {
        Car car = carService.getById(carId);
        session.setAttribute("car", car);

        // Redirect to the GET endpoint
        return "redirect:" + returnPath;
    }
}
