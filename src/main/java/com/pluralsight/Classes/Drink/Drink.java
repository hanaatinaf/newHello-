package com.pluralsight.Classes.Drink;

import com.pluralsight.Abstract.Product;
import com.pluralsight.Enum.Size;

public class Drink extends Product {

    private String flavor;

    /**
     * Creates a new drink.
     *
     * @param name   generic name, e.g., "Drink"
     * @param size   size selected by the user
     * @param flavor flavor description, often from EthiopianDrinkType.getDisplayName()
     */
    public Drink(String name, Size size, String flavor) {
        super(name, size);
        this.flavor = flavor;
    }

    // --- Flavor getters & setters ---

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    /**
     * Calculates the drink price based on size only.
     */
    @Override
    public double calculatePrice() {
        switch (getSize()) {
            case SMALL:
                return 2.00;
            case MEDIUM:
                return 2.50;
            case LARGE:
                return 3.00;
            default:
                return 0.0;
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s, %.2f)",
                getName(),
                flavor,
                getSize().getDisplayName(),
                calculatePrice());
    }
}
