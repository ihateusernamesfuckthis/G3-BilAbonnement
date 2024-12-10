package com.example.g3bilabonnement.entity;

import java.util.ArrayList;
import java.util.List;

public class Subscription {
    private int id;
    private double baseSubscriptionPricePerMonth;
    List<SubscriptionAddon> subscriptionAddons;
    private String subscriptionType;
    private KilometerOption kilometerOption;
    private double totalPricePerMonth;

    public Subscription() {
    }

    public Subscription(int id, double baseSubscriptionPricePerMonth, List<SubscriptionAddon> subscriptionAddons, String subscriptionType, KilometerOption kilometerOption, double totalPricePerMonth) {
        this.id = id;
        this.baseSubscriptionPricePerMonth = baseSubscriptionPricePerMonth;
        this.subscriptionAddons = subscriptionAddons;
        this.subscriptionType = subscriptionType;
        this.kilometerOption = kilometerOption;
        this.totalPricePerMonth = totalPricePerMonth;
    }

    public void calculateTotalPricePerMonth() {
        double addonPricePerMonth = 0.0;
        if (subscriptionAddons != null) {
            for (SubscriptionAddon addon : subscriptionAddons) {
                addonPricePerMonth += addon.getPricePerMonth();
            }
        }
        double kilometerPricePerMonth = (kilometerOption != null) ? kilometerOption.getPricePerMonth() : 0.0;
        this.totalPricePerMonth = baseSubscriptionPricePerMonth + addonPricePerMonth + kilometerPricePerMonth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBaseSubscriptionPrice() {
        return baseSubscriptionPricePerMonth;
    }

    public void setBaseSubscriptionPrice(double baseSubscriptionPrice) {
        this.baseSubscriptionPricePerMonth = baseSubscriptionPrice;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public double getTotalPricePerMonth() {
        return totalPricePerMonth;
    }

    public void setTotalPricePerMonth(double totalPricePerMonth) {
        this.totalPricePerMonth = totalPricePerMonth;
    }

    public List<SubscriptionAddon> getSubscriptionAddons() {
        return subscriptionAddons;
    }

    public void setSubscriptionAddons(List<SubscriptionAddon> subscriptionAddons) {
        this.subscriptionAddons = subscriptionAddons;
    }

    public void addSubscriptionAddon(SubscriptionAddon subscriptionAddon) {
        // Makes sure there is a list
        if (this.subscriptionAddons == null) {
            this.subscriptionAddons = new ArrayList<>(); // <SubscriptionAddon>
        }
        this.subscriptionAddons.add(subscriptionAddon);
    }

    public double getBaseSubscriptionPricePerMonth() {
        return baseSubscriptionPricePerMonth;
    }

    public void setBaseSubscriptionPricePerMonth(double baseSubscriptionPricePerMonth) {
        this.baseSubscriptionPricePerMonth = baseSubscriptionPricePerMonth;
    }

    public KilometerOption getKilometerOption() {
        return kilometerOption;
    }

    public void setKilometerOption(KilometerOption kilometerOption) {
        this.kilometerOption = kilometerOption;
    }
}
