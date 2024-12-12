package com.example.g3bilabonnement.controller;

import com.example.g3bilabonnement.entity.KilometerOption;
import com.example.g3bilabonnement.entity.Subscription;
import com.example.g3bilabonnement.entity.SubscriptionAddon;
import com.example.g3bilabonnement.entity.helper.SelectOption;
import com.example.g3bilabonnement.service.KilometerOptionsService;
import com.example.g3bilabonnement.service.SubscriptionAddonService;
import com.example.g3bilabonnement.service.SubscriptionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private SubscriptionAddonService subscriptionAddonService;
    @Autowired
    private KilometerOptionsService kilometerOptionsService;

    @GetMapping("/new")
    public String createSubscriptionPage(Model model) {
        // formats decimals to 2 decimal but omits trailing zeros
        DecimalFormat decimalFormat = new DecimalFormat("0.##");

        List<SubscriptionAddon> subscriptionAddons = subscriptionAddonService.getAll();
        // Transform the list of SubscriptionAddon into a list of SelectOption to use in formSelectFragment
        // Used for displaying the options in a dropdown
        List<SelectOption> subscriptionAddonSelectOptions = new ArrayList<>(); // <SelectOption>
        for (SubscriptionAddon subscriptionAddon : subscriptionAddons) {
            SelectOption option = new SelectOption(String.valueOf(subscriptionAddon.getId()), subscriptionAddon.getName() + " " + decimalFormat.format(subscriptionAddon.getPricePerMonth()) + "kr./md.");
            subscriptionAddonSelectOptions.add(option);
        }
        model.addAttribute("subscriptionAddonSelectOptions", subscriptionAddonSelectOptions);

        List<SelectOption> subscriptionTypeSelectOptions = new ArrayList<>(); // <SelectOption>
        subscriptionTypeSelectOptions.add(new SelectOption("Limited", "Limited"));
        subscriptionTypeSelectOptions.add(new SelectOption("Unlimited", "Unlimited"));
        model.addAttribute("subscriptionTypeSelectOptions", subscriptionTypeSelectOptions);

        List<KilometerOption> kilometerOptions = kilometerOptionsService.getKilometerOptions();
        List<SelectOption> kilometerOptionsSelectOptions = new ArrayList<>(); // <SelectOption>
        for (KilometerOption kilometerOption : kilometerOptions) {
            SelectOption option = new SelectOption(String.valueOf(kilometerOption.getId()), kilometerOption.getKilometersPerMonth() + "km. " + decimalFormat.format(kilometerOption.getPricePerMonth()) + "kr./md.");
            kilometerOptionsSelectOptions.add(option);
        }
        model.addAttribute("kilometerOptionsSelectOptions", kilometerOptionsSelectOptions);

        return "/dataRegistrator/createSubscription";
    }

    @PostMapping("/create")
    public String createSubscription(@RequestParam double baseSubscriptionPrice, @RequestParam int kmPrMonth,
                                     @RequestParam List<Integer> subscriptionAddons, @RequestParam String subscriptionType,
                                     HttpSession session) {
        Subscription subscription = new Subscription();
        subscription.setBaseSubscriptionPrice(baseSubscriptionPrice);

        KilometerOption kilometerOption = new KilometerOption();
        kilometerOption.setId(kmPrMonth);
        subscription.setKilometerOption(kilometerOption);

        subscription.setSubscriptionType(subscriptionType);

        for (Integer addonId : subscriptionAddons) {
            SubscriptionAddon addon = new SubscriptionAddon();
            addon.setId(addonId);
            subscription.addSubscriptionAddon(addon);
        }

        // setting subscription on session to access it from the next page and keep it during refreshes
        subscription.setId(subscriptionService.add(subscription));
        session.setAttribute("subscriptionId", subscription.getId());

        String returnPath = (String) session.getAttribute("returnPath");
        if (returnPath == null) {
            returnPath = "/"; // Default return path
        }

        // Redirect to the GET endpoint
        return "redirect:" + returnPath;
    }

}
