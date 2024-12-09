package com.example.g3bilabonnement.service;

import com.example.g3bilabonnement.entity.KilometerOption;
import com.example.g3bilabonnement.entity.Subscription;
import com.example.g3bilabonnement.entity.SubscriptionAddon;
import com.example.g3bilabonnement.repository.KilometerOptionsRepository;
import com.example.g3bilabonnement.repository.SubscriptionAddonRepository;
import com.example.g3bilabonnement.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SubscriptionAddonRepository subscriptionAddonRepository;
    @Autowired
    KilometerOptionsRepository kilometerOptionsRepository;

    public Subscription getById(int id) {
        Subscription subscription = subscriptionRepository.getById(id);
        List<SubscriptionAddon> addons = subscriptionAddonRepository.findBySubscriptionId(id);
        // Attach addons to the subscription (if Subscription has an addons field)
        subscription.setSubscriptionAddons(addons);

        KilometerOption kilometerOption = kilometerOptionsRepository.getById(subscription.getKilometerOption().getId());
        subscription.setKilometerOption(kilometerOption);

        subscription.calculateTotalPricePerMonth();
        return subscription;
    }


    public int add(Subscription subscription) {
        int id = subscriptionRepository.add(subscription);
        subscription.setId(id);

        if (subscription.getSubscriptionAddons() == null || subscription.getSubscriptionAddons().isEmpty()) {
            return id;
        }

        for(SubscriptionAddon addon : subscription.getSubscriptionAddons()) {
            subscriptionAddonRepository.addSubscriptionAddonToSubscription(subscription.getId(), addon.getId());
        }

        return id;
    }
}
