package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.CarModelLimit;
import com.example.g3bilabonnement.repository.CarModelLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarModelLimitService {
    @Autowired
    private CarModelLimitRepository carModelLimitRepository;

    public List<CarModelLimit> getCarModelLimits() {
        return carModelLimitRepository.getCarModelLimits();
    }

    public void saveCarModelLimit(CarModelLimit carModelLimit) {
        carModelLimitRepository.saveCarModelLimit(carModelLimit);
    }
}
