package com.example.g3bilabonnement.entity;

public class CarModelLimit implements Comparable<CarModelLimit> {
    private int id;
    private CarModel carModel;
    private int minLimit;
    private int availableCars;

    public CarModelLimit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public int getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(int minLimit) {
        this.minLimit = minLimit;
    }

    public int getAvailableCars() {
        return availableCars;
    }

    public void setAvailableCars(int availableCars) {
        this.availableCars = availableCars;
    }

    // Sort by if under minLimit then by availableCars
    public int compareTo(CarModelLimit other) {
        boolean thisBelowLimit = this.availableCars < this.minLimit;
        boolean otherBelowLimit = other.availableCars < other.minLimit;

        if (thisBelowLimit && !otherBelowLimit) {
            return -1; // This comes before other
        } else if (!thisBelowLimit && otherBelowLimit) {
            return 1; // Other comes before this
        } else if (this.availableCars == other.availableCars){
            return Integer.compare(other.minLimit, this.minLimit); // Compare by minLimit if both are same status and same availableCars, larger number first
        } else {
            return Integer.compare(this.availableCars, other.availableCars); // Compare by availableCars if both are same status
        }
    }
}

