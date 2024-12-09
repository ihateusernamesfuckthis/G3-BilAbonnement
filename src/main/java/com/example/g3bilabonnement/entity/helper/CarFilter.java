package com.example.g3bilabonnement.entity.helper;

public class CarFilter {
    private String status;
    private int id;
    private String vehicleNumber;
    private boolean missingDamageReport;

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
    public boolean isMissingDamageReport() {// Checkbox kan have tre værdier og derfor skal wrapper klassen bruges
        return missingDamageReport;
    }
    public void setMissingDamageReport(boolean missingDamageReport) {
        this.missingDamageReport = missingDamageReport;
    }
}
