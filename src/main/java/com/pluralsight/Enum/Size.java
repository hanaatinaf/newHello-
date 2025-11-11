package com.pluralsight.Enum;
// Enum Representing the avalable sizes for products

public enum Size {
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large");
    private  final String  displayName;

    //  contractor
    Size(String displayName) {
        this.displayName =  displayName;
    }
    // Getter methods
    public  String getDisplayName(){
        return displayName;
    }
}
