package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.Car;
import com.example.g3bilabonnement.repository.CarRepository;
import com.example.g3bilabonnement.repository.FinalSettlementRepository;
import com.example.g3bilabonnement.entity.FinalSettlement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinalSettlementService {

    @Autowired
    private FinalSettlementRepository finalSettlementRepository;

    public void add(FinalSettlement finalSettlement){
        finalSettlementRepository.add(finalSettlement);
    }

    public FinalSettlement getByCarId(int carId){
        return finalSettlementRepository.getByCarId(carId);
    }


}
