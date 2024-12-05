package com.example.g3bilabonnement.Service;

import com.example.g3bilabonnement.Entity.Car;
import com.example.g3bilabonnement.Repository.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    CarRepo carRepo;
    public Car getCarById(int id) {
        return carRepo.getCarById(id);//Her finder jeg bil på bilens id
    }
}
