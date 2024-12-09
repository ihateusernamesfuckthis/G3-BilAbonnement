package com.example.g3bilabonnement.entity;

public class Car {
    private int id;
    private String imageUrl;
    private String vehicleNumber;
    private String vinNumber;
    private String brand;
    private String model;
    private String equipmentLevel;
    private String powerSourceType;
    private String transmissionType;
    private double netPrice;
    private double registrationTax;
    private double co2Emissions;
    private String status;
    public Car() {
    }

    public Car(int id, String imageUrl, String vehicleNumber, String vinNumber, String brand, String model, String equipmentLevel, String powerSourceType, String transmissionType, double netPrice, double registrationTax, double co2Emissions, String status) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.vehicleNumber = vehicleNumber;
        this.vinNumber = vinNumber;
        this.brand = brand;
        this.model = model;
        this.equipmentLevel = equipmentLevel;
        this.powerSourceType = powerSourceType;
        this.transmissionType = transmissionType;
        this.netPrice = netPrice;
        this.registrationTax = registrationTax;
        this.co2Emissions = co2Emissions;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String newImageUrl) {
        this.imageUrl = newImageUrl;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String newVehicleNumber) {
        this.vehicleNumber = newVehicleNumber;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String newVinNumber) {
        this.vinNumber = newVinNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String newBrand) {
        this.brand = newBrand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String newModel) {
        this.model = newModel;
    }

    public String getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(String newEquipmentLevel) {
        this.equipmentLevel = newEquipmentLevel;
    }

    public String getPowerSourceType() {
        return powerSourceType;
    }

    public void setPowerSourceType(String newPowerSourceType) {
        this.powerSourceType = newPowerSourceType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String newTransmissionType) {
        this.transmissionType = newTransmissionType;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(double newNetPrice) {
        this.netPrice = newNetPrice;
    }

    public double getRegistrationTax() {
        return registrationTax;
    }

    public void setRegistrationTax(double newRegistrationTax) {
        this.registrationTax = newRegistrationTax;
    }

    public double getCo2Emissions() {
        return co2Emissions;
    }

    public void setCo2Emissions(double newCo2Emissions) {
        this.co2Emissions = newCo2Emissions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
