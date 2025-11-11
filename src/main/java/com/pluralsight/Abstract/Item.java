package com.pluralsight.Abstract;

import com.pluralsight.Enum.ItemType;
import com.pluralsight.Enum.Size;
import com.pluralsight.Interfaces.Customizable;

import java.util.*;

public abstract  class Item extends  Product implements Customizable {
    private ItemType  type;
    private List<Topping> toppings = new ArrayList<>() ;
    private boolean isSpecialized;

    public Item(ItemType type,Size size, String name) {
        super(size, name);
        this.type = type;
    }

    public  ItemType getType(){

        return type;
    };
    public  void setType(ItemType type){
        this.type = type;

    }
    public List<Topping> getToppings(){

        return toppings;
    };
   public  boolean isSpecialized(){

        return isSpecialized;
    }
    public  void setSpecialized(boolean specialized){
       isSpecialized = specialized;

    }
    @Override
    public void addTopping(Topping topping){
       toppings.add(topping);


    };
    @Override
    public  void removeTopping(Topping topping){
        toppings.remove(topping);

    };
    @Override
    public String toString(){
        return getName()+ " ( " + getSize().getDisplayName() + " ) ";
    }
    @Override
    public abstract  double calculatePrice();




}
