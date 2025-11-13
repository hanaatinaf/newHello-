package com.pluralsight.Classes;

import com.pluralsight.Abstract.Product;
import com.pluralsight.Abstract.Topping;
import com.pluralsight.Classes.Drink.Drink;
import com.pluralsight.Classes.Toppings.PremiumTopping;
import com.pluralsight.Enum.Size;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;




/**
 * Represents a customer's order.
 * An order contains multiple products (main dishes, drinks, sides),
 * knows when it was created, and can generate a receipt string.
 */
public class Order {

    private String id;
    private LocalDateTime dateTime;
    private final List<Product> products;
    private boolean isCompleted;

    private static final DateTimeFormatter ID_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    /**
     * Creates a new order with a generated id and current timestamp.
     */
    public Order() {
        this.dateTime = LocalDateTime.now();
        this.id = dateTime.format(ID_FORMAT); // example: 20251112-153045
        this.products = new ArrayList<>();
        this.isCompleted = false;
    }

    // --- Basic getters ---

    public String getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Returns an unmodifiable list so external code cannot
     * directly change the internal list.
     */
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    // --- Order operations ---

    /**
     * Adds a product (EthiopianFoodItem, Drink, Side) to the order.
     */
    public void addProduct(Product product) {
        if (product != null && !isCompleted) {
            products.add(product);
        }
    }

    /**
     * Removes a product from the order.
     */
    public void removeProduct(Product product) {
        if (!isCompleted) {
            products.remove(product);
        }
    }

    /**
     * Calculates the total cost of all products in the order.
     */

    public double calculateTotal() {
        // Uses Java Streams to sum up product prices
        return products.stream()
                .mapToDouble(Product::calculatePrice)
                .sum();
    }
    /**
     * Marks this order as completed (usually after checkout).
     * You could also refresh the timestamp here if needed.
     */
    public void completeOrder() {
        this.isCompleted = true;
        // Optionally update dateTime when order is completed:
        // this.dateTime = LocalDateTime.now();
        // this.id = dateTime.format(ID_FORMAT);
    }


    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("===== Ethiopian Restaurant Receipt =====\n");
        sb.append("Order ID : ").append(id).append("\n");
        sb.append("Date/Time: ").append(dateTime).append("\n");
        sb.append("---------------------------------------\n");

        double orderTotal = 0.0;

        // Line items
        for (Product product : products) {
            double linePrice = product.calculatePrice();
            orderTotal += linePrice;

            // Main line: product name (and size if available)
            String lineName = product.getName();
            if (product.getSize() != null) {
                lineName += " (" + product.getSize().getDisplayName() + ")";
            }

            sb.append(String.format("%-30s %6.2f%n", lineName, linePrice));

            // -----------------------------------------------
            // Extra breakdown ONLY for EthiopianFoodItem
            // -----------------------------------------------
            if (product instanceof EthiopianFoodItem) {
                EthiopianFoodItem food = (EthiopianFoodItem) product;
                Size size = food.getSize();
                double sizeMultiplier = size.getPriceMultiplier();

                // 1) Base component (same logic as in EthiopianFoodItem.calculatePrice)
                double basePrice;
                switch (food.getType()) {
                    case INJERA_COMBO:
                        basePrice = 19.00;
                        break;
                    case TIBS_PLATE:
                        basePrice = 18.00;
                        break;
                    case KITFO_PLATE:
                        basePrice = 20.88;
                        break;
                    case BEYAYNETU:
                        basePrice = 25.99;
                        break;
                    default:
                        basePrice = 9.00;
                        break;
                }
                double baseComponent = basePrice * sizeMultiplier;

                // 2) Special add-on component
                double specialComponent = 0.0;
                if (food.isSpecialized()) {
                    specialComponent = 2.00 * sizeMultiplier;
                }

                // 3) Toppings component
                double toppingsTotal = 0.0;

                sb.append("   Details:\n");
                sb.append(String.format("     Base (%s x %.2f): %.2f%n",
                        food.getType().getDisplayName(),
                        sizeMultiplier,
                        baseComponent));

                if (specialComponent > 0) {
                    sb.append(String.format("     Special add-on:      %.2f%n",
                            specialComponent));
                }

                if (food.getToppings() != null && !food.getToppings().isEmpty()) {
                    sb.append("     Toppings:\n");
                    for (Topping t : food.getToppings()) {
                        double toppingPrice = t.getPrice(size);
                        toppingsTotal += toppingPrice;

                        if (t instanceof PremiumTopping) {
                            PremiumTopping pt = (PremiumTopping) t;
                            sb.append(String.format("       • %s%s  %.2f%n",
                                    t.getName(),
                                    pt.isExtra() ? " (EXTRA)" : "",
                                    toppingPrice));
                        } else {
                            sb.append(String.format("       • %s  %.2f%n",
                                    t.getName(),
                                    toppingPrice));
                        }
                    }
                    sb.append(String.format("     Toppings total:      %.2f%n",
                            toppingsTotal));
                } else {
                    sb.append("     (No toppings)\n");
                }

                sb.append("\n");
            }
        }

        sb.append("---------------------------------------\n");
        sb.append(String.format("TOTAL:%34.2f%n", orderTotal));
        sb.append("=======================================\n");

        return sb.toString();
    }

}
