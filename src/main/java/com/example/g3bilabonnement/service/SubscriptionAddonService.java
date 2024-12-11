package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.SubscriptionAddon;
import com.example.g3bilabonnement.repository.SubscriptionAddonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionAddonService {
    @Autowired
    private SubscriptionAddonRepository subscriptionAddonRepository;

    public List<SubscriptionAddon> getAll() {
        return subscriptionAddonRepository.getAll();
    }

    public List<SubscriptionAddon> findBySubscriptionId(Integer subscriptionId) {
        return subscriptionAddonRepository.findBySubscriptionId(subscriptionId);
    }

    public void addSubscriptionAddonToSubscription(int subscriptionId, int addonId) {
        subscriptionAddonRepository.addSubscriptionAddonToSubscription(subscriptionId, addonId);
    }
}
