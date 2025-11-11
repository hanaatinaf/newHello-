package com.pluralsight.Abstract;

import com.pluralsight.Enum.Size;
import com.pluralsight.Interfaces.Priceable;

public abstract class Product  implements Priceable {
    // private fields with protected accessors
    private String name;
    private Size size;

    public Product(Size size, String name) {
        this.size = size;
        this.name = name;
    }

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

    // abstraction method to be implemnted by subclass
    public abstract  double calculatePrice();

    @Override
    public String toString(){
        return String.format(("%s (%s)") , name ,size.getDisplayName());
    }
}
