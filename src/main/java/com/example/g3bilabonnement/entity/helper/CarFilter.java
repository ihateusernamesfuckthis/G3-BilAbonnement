package com.example.g3bilabonnement.entity.helper;

public class CarFilter {
    private String status;
    private int id;
    private String vehicleNumber;

    public CarFilter(String status, int id, String vehicleNumber) {
        this.status = status;
        this.id  = id;
        this.vehicleNumber = vehicleNumber;
    }
    public CarFilter(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
