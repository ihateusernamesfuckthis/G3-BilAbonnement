package com.example.g3bilabonnement.Service;

import com.example.g3bilabonnement.Repository.DamageSpecificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DamageSpecificationService {
    @Autowired
    DamageSpecificationRepo damageSpecificationRepo;
}