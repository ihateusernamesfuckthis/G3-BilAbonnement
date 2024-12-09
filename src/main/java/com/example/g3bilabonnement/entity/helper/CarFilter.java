package com.example.g3bilabonnement.entity.helper;

import org.springframework.web.bind.annotation.ModelAttribute;


public class CarFilter {
    private String status;
    private String vehicleNumber;

    public CarFilter() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
