package com.example.g3bilabonnement.entity;

public class Location {
    private int id;
    private String address;
    private String city;
    private String zipCode;

    public Location() {
    }

    public Location(int id, String address, String city, String zipCode) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
