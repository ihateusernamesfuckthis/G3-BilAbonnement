package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.KilometerOption;
import com.example.g3bilabonnement.repository.KilometerOptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KilometerOptionsService {
    @Autowired
    private KilometerOptionsRepository kilometerOptionRepository;

    public List<KilometerOption> getKilometerOptions() {
        return kilometerOptionRepository.getKilometerOptions();
    }

    public KilometerOption getById(int id) {
        return kilometerOptionRepository.getById(id);
    }
}
