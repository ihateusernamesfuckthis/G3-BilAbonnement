package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.Location;
import com.example.g3bilabonnement.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public int add(Location location){
        return locationRepository.add(location);
    }
}
