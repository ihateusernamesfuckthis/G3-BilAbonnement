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

    @Autowired
    private LocationService locationService;

    public Renter getById(int id) {
        Renter renter = renterRepository.getById(id);
        renter.setLocation(locationService.getById(renter.getLocation().getId()));
        return renter;
    }

    public List<Renter> searchByFilter(RenterFilter renterFilter) {
        List<Renter> renters = renterRepository.searchByFilter(renterFilter);
        for (Renter renter : renters) {
            renter.setLocation(locationService.getById(renter.getLocation().getId()));
        }
        return renters;
    }
}
