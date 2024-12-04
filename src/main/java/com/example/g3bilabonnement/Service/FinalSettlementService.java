package com.example.g3bilabonnement.Service;

import com.example.g3bilabonnement.Repository.FinalSettlementRepository;
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

}
