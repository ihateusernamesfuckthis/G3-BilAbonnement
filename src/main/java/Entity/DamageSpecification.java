package Entity;

public class DamageSpecification {

    String damageDescription;
    Double damagePrice;

    public DamageSpecification() {
    }
    public DamageSpecification(String damageDescription, Double damagePrice){
        this.damageDescription = damageDescription;
        this.damagePrice = damagePrice;
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
