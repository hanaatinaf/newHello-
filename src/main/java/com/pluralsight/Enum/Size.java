package com.pluralsight.Enum;
// Enum Representing the avalable sizes for products
// Size.java

public enum Size {
    SMALL("Small", 1.0),
    MEDIUM("Medium", 1.5),
    LARGE("Large", 2.0);

    private final String displayName;
    private final double priceMultiplier;

    Size(String displayName, double priceMultiplier) {
        this.displayName = displayName;
        this.priceMultiplier = priceMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    @Override
    public String toString() {
        return displayName;
    }
}