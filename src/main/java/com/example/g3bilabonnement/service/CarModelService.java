package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.CarModel;
import com.example.g3bilabonnement.repository.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarModelService {
    @Autowired
    private CarModelRepository carModelRepository;

    public List<CarModel> getAllCarModel() {
        return carModelRepository.getAllCarModel();
    }
}
