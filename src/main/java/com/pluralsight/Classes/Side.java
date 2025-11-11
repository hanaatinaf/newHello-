package com.pluralsight.Classes;

import com.pluralsight.Abstract.Product;
import com.pluralsight.Enum.Size;

public class Side extends Product {
    public Side( String name ,Size size) {
        super(size, name);
    }

    @Override
    public double calculatePrice() {
        switch (getSize()){
            case SMALL :
                return  2.00;
            case MEDIUM:
                return  3.50;
            case LARGE:
                return  5.00;
            default:
                return  0;
        }
    }
    @Override
    public  String toString(){
        return  getName() + "( " + getSize().getDisplayName() +  " ) ";
    }
}
