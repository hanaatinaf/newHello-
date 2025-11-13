package com.pluralsight.Abstract;

import com.pluralsight.Enum.Size;

public abstract class Topping {

    private String name;
    private double priceSmall;
    private double priceMedium;
    private double priceLarge;

    protected Topping(String name) {
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // --- Price getters / setters ---

    public double getPrice(Size size) {
        switch (size) {
            case SMALL:
                return priceSmall;
            case MEDIUM:
                return priceMedium;
            case LARGE:
                return priceLarge;
            default:
                return 0.0;
        }
    }

    public void setPrice(Size size, double price) {
        switch (size) {
            case SMALL:
                this.priceSmall = price;
                break;
            case MEDIUM:
                this.priceMedium = price;
                break;
            case LARGE:
                this.priceLarge = price;
                break;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
