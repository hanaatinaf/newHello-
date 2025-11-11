package com.pluralsight.Abstract;

import com.pluralsight.Enum.Size;

public abstract  class Topping {
    // protected fields for sub access
    protected String name;
    protected  double priceSmall;
    protected  double priceMedium;
    protected  double priceLarge;

    public Topping(String name, double priceSmall, double priceMedium, double priceLarge) {
        this.name = name;
        this.priceSmall = priceSmall;
        this.priceMedium = priceMedium;
        this.priceLarge = priceLarge;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public double getPrice(Size size){
        switch (size){
            case SMALL:
                return  priceSmall;
            case MEDIUM :
                return  priceMedium;
            case LARGE:
                return  priceLarge;
            default:
                return  0.0;
        }
    }
    public void setPrice(Size size, double price ){
        switch (size) {
            case SMALL:
                priceSmall = price;
                break;
            case MEDIUM:
                priceMedium = price;
                break;

            case LARGE:
                priceLarge = price;
                break;
        }
    }
    @Override
    public String toString(){
            return name; }
}
