package com.pluralsight.Classes;

import com.pluralsight.Abstract.Topping;
import com.pluralsight.Enum.Size;

public class PremiumTopping extends Topping {
    private  boolean isExtra;

    public PremiumTopping(String name, double priceSmall, double priceMedium, double priceLarge, boolean isExtra) {
        super(name, priceSmall, priceMedium, priceLarge);
        this.isExtra = isExtra;
    }

    public boolean isExtra() {
        return isExtra;
    }

    public void setExtra(boolean extra) {
        this.isExtra = extra;
    }
    @Override
    public  double getPrice(Size size){
        double basePrice = super.getPrice(size);

        // extra premium cost +4 regardless  of the size
        if (isExtra){
            return  basePrice + 4;
        }
        return  basePrice;
    }
    @Override
    public  String toString(){
        return  getName() + (isExtra ? " Permimum +$4" : " ");
    }

}
