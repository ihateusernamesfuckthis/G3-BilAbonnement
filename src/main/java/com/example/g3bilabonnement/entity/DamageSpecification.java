package com.example.g3bilabonnement.entity;

public class DamageSpecification {

    private int id;
    private String damageDescription;
    private Double damagePrice;
    public DamageSpecification() {
    }
    public DamageSpecification(int id, String damageDescription, Double damagePrice){
        this.id = id;
        this.damageDescription = damageDescription;
        this.damagePrice = damagePrice;
    }

    public int getId(){
        return id;
    }

    public void setId(int newId){
        this.id= newId;
    }
    public String getDamageDescription() {
        return damageDescription;
    }

    public void setDamageDescription(String newDamageDescription) {
        this.damageDescription = newDamageDescription;
    }

    public Double getDamagePrice() {
        return damagePrice;
    }

    public void setDamagePrice(Double newDamagePrice) {
        this.damagePrice = newDamagePrice;
    }
}
