package com.pluralsight.Classes;

import com.pluralsight.Abstract.Item;
import com.pluralsight.Enum.ItemType;
import com.pluralsight.Enum.Size;


public class EthiopianFoodItem extends Item {

    public EthiopianFoodItem(ItemType type, Size size) {
        super(type,  size, type.getDisplayName() );
    }

    @Override
    public double calculatePrice() {
        double basePrice = 0.0;
        switch (getType()){
            case BEYAYNETU :
                if (getSize() == Size.SMALL) basePrice = 12;
                else if ((getSize() == Size.MEDIUM))  basePrice = 15 ;
                else  basePrice = 18;
                break;

            case TIBS_PLATE:
                if (getSize() == Size.SMALL) basePrice = 13;
                else if ((getSize() == Size.MEDIUM))  basePrice = 16 ;
                else  basePrice = 19;
                break;

            case KITFO_PLATE:
                if (getSize() == Size.SMALL) basePrice = 14;
                else if ((getSize() == Size.MEDIUM))   basePrice = 17 ;  else  basePrice = 20;
                break;
            case INJERA_COMBO:
                if (getSize() == Size.SMALL)  basePrice = 10;
                else if ((getSize() == Size.MEDIUM))  basePrice = 13 ;
                else  basePrice = 16;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + getType());
        }
        // add typping cost using stream
        double toppingTotal = getToppings().stream().mapToDouble((t -> t.getPrice(getSize()))).sum();


        return basePrice + toppingTotal;
    }



}
