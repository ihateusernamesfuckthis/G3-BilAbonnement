package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.Renter;
import com.example.g3bilabonnement.entity.helper.RenterFilter;
import com.example.g3bilabonnement.repository.RenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RenterService {
    @Autowired
    private RenterRepository renterRepository;

    public Renter getById(int id) {
        return renterRepository.getById(id);
    }

    public List<Renter> searchByFilter(RenterFilter renterFilter) {
        return renterRepository.searchByFilter(renterFilter);
    }
}
