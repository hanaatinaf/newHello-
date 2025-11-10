package com.plursalsight;

public enum  ItemType {
    INJERA_COMBO("Injera Combo"),
    TIBS_PLATE("Tibs Plate"),
    KITFO_PLATE("Kitfo Plate"),
    BEYAYNETU("Beyaynetu Vegetarian");

    private final String displayName;

    ItemType(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName(){
        return displayName;
    }
}
