package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.Subscription;
import com.example.g3bilabonnement.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription getById(int id) {
        return subscriptionRepository.getById(id);
    }
}
