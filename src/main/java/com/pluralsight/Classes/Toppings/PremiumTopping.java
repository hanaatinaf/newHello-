package com.pluralsight.Classes.Toppings;

import com.pluralsight.Abstract.Topping;
import com.pluralsight.Enum.Size;

/**
 * Premium toppings (proteins, cheeses, etc.).
 * These usually cost additional money and may have an "extra" flag.
 */
public class PremiumTopping extends Topping {

    private boolean isExtra;

    public PremiumTopping(String name, boolean isExtra) {
        super(name);
        this.isExtra = isExtra;
    }

    public boolean isExtra() {
        return isExtra;
    }

    public void setExtra(boolean extra) {
        isExtra = extra;
    }

    /**
     * Returns the price for this topping at the given size.
     * If isExtra == true, we charge a bit more.
     *
     * You can tune the multiplier or replace this method with
     * explicit "extra meat" prices from your menu if you prefer.
     */
    @Override
    public double getPrice(Size size) {
        double basePrice = super.getPrice(size);

        if (isExtra) {
            // Example: charge 50% more for "extra" premium topping
            return basePrice * 1.5;
        }

        return basePrice;
    }
}
