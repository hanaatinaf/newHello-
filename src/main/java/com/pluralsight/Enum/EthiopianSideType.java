package com.pluralsight.Enum;



 // Ethiopian side items included or optional.

public enum EthiopianSideType {

    AYIB("Ethiopian Cottage Cheese"),
    AWAZE("Berbere Spice Sauce"),
    MITMITA("Spicy Chili Powder Mix"),
    SALATA("Fresh Salad"),
    EXTRA_INJERA("Additional Injera");

    private final String displayName;

    EthiopianSideType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
