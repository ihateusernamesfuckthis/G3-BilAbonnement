package com.example.g3bilabonnement.entity.helper;

public class SelectOption {
    private String value; // Represents the dropdown value
    private String label; // Represents the display text

    public SelectOption(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
