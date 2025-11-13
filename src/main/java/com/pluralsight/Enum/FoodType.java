package com.pluralsight.Enum;

public enum FoodType {
    INJERA_COMBO("Injera Combo"),
    TIBS_PLATE("Tibs Plate"),
    KITFO_PLATE("Kitfo Plate"),
    BEYAYNETU("Beyaynetu Vegetarian");

    private final String displayName;

    FoodType(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName(){
        return displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }

}
