package com.pluralsight.Classes;

import com.pluralsight.Abstract.Product;
import com.pluralsight.Abstract.Topping;
import com.pluralsight.Classes.Drink.Drink;


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

    /**
     * Builds a text receipt for this order.
     * This string is used both for displaying on screen and
     * for writing to the receipt file.
     */
    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("===== Ethiopian Restaurant Receipt =====\n");
        sb.append("Order ID : ").append(id).append("\n");
        sb.append("Date/Time: ").append(dateTime).append("\n");
        sb.append("---------------------------------------\n");

        // Line items
        for (Product product : products) {
            String line = String.format("%-30s %6.2f\n",
                    product.toString(),
                    product.calculatePrice());
            sb.append(line);
        }

        sb.append("---------------------------------------\n");
        sb.append(String.format("TOTAL: %34.2f\n", calculateTotal()));
        sb.append("=======================================\n");

        return sb.toString();
    }
}
