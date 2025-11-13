package com.pluralsight.Classes.Sides;

import com.pluralsight.Abstract.Product;
import com.pluralsight.Enum.Size;

public class Side extends Product {

    // Flat price for a side/main side from the assignment.
    private static final double SIDE_PRICE = 1.50;


    public Side(String name, Size size) {
        super(name, size);
    }


    @Override
    public double calculatePrice() {
        return SIDE_PRICE;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%.2f)",
                getName(),
                getSize().getDisplayName(),
                calculatePrice());
    }
}
