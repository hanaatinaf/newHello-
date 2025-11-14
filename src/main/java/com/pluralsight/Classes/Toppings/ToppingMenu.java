package com.pluralsight.Classes.Toppings;


import com.pluralsight.Classes.EthiopianFoodItem;
import com.pluralsight.Classes.Toppings.PremiumTopping;
import com.pluralsight.Classes.Toppings.RegularTopping;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Handles adding regular and premium toppings to an EthiopianFoodItem.
 */
public class ToppingMenu {

    private final Scanner scanner;

    public ToppingMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Lets the user add regular or premium toppings to the given item.
     */
    public void promptForToppings(EthiopianFoodItem item) {
        System.out.print("Would you like to add toppings? (y/n): ");
        String input = scanner.next().trim().toLowerCase();
        if (!input.startsWith("y")) {
            return;
        }

        // Predefined Ethiopian toppings
        String[] regularOptions = {
                "Gomen (Collard Greens)",
                "Misir Wot (Spicy Lentils)",
                "Shiro (Chickpea Stew)",
                "Fosolia (Green Beans & Carrots)",
                "Atakilt (Cabbage & Carrot Mix)"
        };

        String[] premiumOptions = {
                "Extra Tibs",
                "Extra Kitfo",
                "Doro Wot (Chicken Stew)",
                "Bozena Shiro (Meat Chickpea Stew)"
        };

        boolean adding = true;

        while (adding) {
            System.out.println("➕  Add topping:");
            System.out.println("1) Regular topping");
            System.out.println("2) Premium topping");
            System.out.println("0) Done adding toppings");
            System.out.print("👉  Choose an option: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    System.out.println("🍀 Regular Toppings:");
                    for (int i = 0; i < regularOptions.length; i++) {
                        System.out.printf("%d) %s%n", i + 1, regularOptions[i]);
                    }
                    System.out.print("👉  Choose a topping: ");
                    int regChoice = readIntInRange(1, regularOptions.length);
                    String regName = regularOptions[regChoice - 1];

                    item.addTopping(new RegularTopping(regName));
                    System.out.println("✅  Added regular topping: " + regName);
                    break;

                case 2:
                    System.out.println("🌶️ Premium Toppings:");
                    for (int i = 0; i < premiumOptions.length; i++) {
                        System.out.printf("%d) %s%n", i + 1, premiumOptions[i]);
                    }
                    System.out.print("👉  Choose a topping: ");
                    int premChoice = readIntInRange(1, premiumOptions.length);
                    String premName = premiumOptions[premChoice - 1];

                    System.out.print("Is this EXTRA? (y/n): ");
                    String extraInput = scanner.next().trim().toLowerCase();
                    boolean isExtra = extraInput.startsWith("y");

                    PremiumTopping premium = new PremiumTopping(premName, isExtra);
                    item.addTopping(premium);
                    System.out.println("✅  Added premium topping: " + premName +
                            (isExtra ? " (EXTRA)" : ""));
                    break;

                case 0:
                    adding = false;
                    break;

                default:
                    System.out.println("⚠️  Invalid option.");
            }
        }
    }

    // --- local input helpers for this menu ---

    private int readInt() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid number: ");
                scanner.next(); // clear invalid input
            }
        }
    }

    private int readIntInRange(int min, int max) {
        int value;
        while (true) {
            value = readInt();
            if (value >= min && value <= max) {
                return value;
            }
            System.out.print("Please enter a number between " + min + " and " + max + ": ");
        }
    }
}
