package com.pluralsight.Classes.Toppings;

import com.pluralsight.Abstract.Topping;
import com.pluralsight.Enum.Size;


public class PremiumTopping extends Topping {

    private boolean isExtra;

    public PremiumTopping(String name, boolean isExtra) {
        super(name);
        this.isExtra = isExtra;

        // Base premium topping prices
        setPrice(Size.SMALL, 1.75);
        setPrice(Size.MEDIUM, 2.25);
        setPrice(Size.LARGE, 2.75);
    }

    public boolean isExtra() {
        return isExtra;
    }

    public void setExtra(boolean extra) {
        isExtra = extra;
    }


    @Override
    public double getPrice(Size size) {

        double basePrice = super.getPrice(size);

        if (isExtra) {
            // add 1.50 more for EXTRA
            return basePrice +=  1.5;
        }

        return basePrice;
    }
}
