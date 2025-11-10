package com.plursalsight;

import java.util.List;

public interface Customizable {
    void addTopping(Topping topping);
    void removeTopping(Topping topping);
    List<Topping> getToppings();
}
