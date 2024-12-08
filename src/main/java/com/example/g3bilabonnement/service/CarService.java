package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.entity.helper.CarFilter;
import com.example.g3bilabonnement.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car getById(int id) {
        return carRepository.getById(id);
    }

    public List<Car> searchByFilter(CarFilter carFilter) {
        return carRepository.searchByFilter(carFilter);
    }

    public void updateCarStatus(Car car, String newStatus){
        carRepository.updateCarStatus(car, newStatus);
    }

    public List<String> getCarStatuses() {
        return carRepository.getCarStatuses();
    }
}
