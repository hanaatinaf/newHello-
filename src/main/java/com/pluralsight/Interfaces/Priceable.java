package com.pluralsight.Interfaces;
// interface for price calculation
// hiding implenation deatils


// Anything that has a price (food, drink, side) must implement this.
public interface Priceable {
    double calculatePrice();
}
