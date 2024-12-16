package com.example.g3bilabonnement.entity;

public class CarModel {
    private int id; // Id of the car model (Brand is a foreign key, and always connected to a car model)
    private String brand;
    private String model;

    public CarModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}

