package com.pluralsight.Classes.Toppings;

import com.pluralsight.Abstract.Topping;
import com.pluralsight.Enum.Size;


/**
 * Regular toppings are usually included in the base price.
 * By default we set their prices to 0 for all sizes.
 */
public class RegularTopping extends Topping {

    public RegularTopping(String name) {
        super(name);

        // Regular toppings are "included", so set all size prices to 0.
        setPrice(Size.SMALL, 0.0);
        setPrice(Size.MEDIUM, 0.0);
        setPrice(Size.LARGE, 0.0);
    }
}
