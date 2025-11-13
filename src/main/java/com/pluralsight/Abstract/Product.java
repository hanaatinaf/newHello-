package com.pluralsight.Abstract;

import com.pluralsight.Enum.Size;
import com.pluralsight.Interfaces.Priceable;


/**
 * Base class for all products in the shop (EthiopianFoodItem, Drink, Side).
 * It stores common fields and forces subclasses to implement calculatePrice().
 */
public abstract class Product implements Priceable {

    private String name;
    private Size size;

    protected Product(String name, Size size) {
        this.name = name;
        this.size = size;
    }

    // Getters & setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Subclasses MUST provide their own price logic
     * (using size multiplier, toppings, etc.).
     */
    @Override
    public abstract double calculatePrice();

    @Override
    public String toString() {
        return name + " (" + size.getDisplayName() + ")";
    }
}
