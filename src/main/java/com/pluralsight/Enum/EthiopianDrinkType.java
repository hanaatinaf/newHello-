package com.pluralsight.Enum;

/**
 * Represents authentic Ethiopian drinks available in the restaurant.
 */
public enum EthiopianDrinkType {

    SHAI("Spiced Black Tea"),
    TEJ("Honey Wine (Non-Alcoholic Version)"),
    TELLA("Barley Drink (Non-Alcoholic Version)"),
    SPRIS("Layered Mixed Juice"),
    MANGO_JUICE("Fresh Mango Juice"),
    LEMON_JUICE("Fresh Lemon Juice");

    private final String displayName;

    EthiopianDrinkType(String displayName) {
        this.displayName = displayName;
    }

    // For printing inside menus and receipts
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
