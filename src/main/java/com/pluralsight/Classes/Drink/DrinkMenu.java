package com.pluralsight.Classes.Drink;



import com.pluralsight.Classes.Drink.Drink;
import com.pluralsight.Enum.EthiopianDrinkType;
import com.pluralsight.Enum.Size;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Handles drink selection and Drink creation.
 */
public class DrinkMenu {

    private final Scanner scanner;

    public DrinkMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Shows drink menu and returns the chosen Drink.
     */
    public Drink handleAddDrink() {
        System.out.println();
        System.out.println("🥤  መጠጥ መጨመሪያ | ADD DRINK");
        System.out.println("────────────────────────────────────────");

        // 1) Choose size
        Size size = promptForSize();

        // 2) Show available Ethiopian drinks
        System.out.println("እባክዎ መጠጥ ይምረጡ | Choose a drink type:");
        EthiopianDrinkType[] drinkTypes = EthiopianDrinkType.values();
        for (int i = 0; i < drinkTypes.length; i++) {
            System.out.printf("%d) %s%n", i + 1, drinkTypes[i].getDisplayName());
        }
        System.out.print("👉  Enter choice: ");
        int choice = readIntInRange(1, drinkTypes.length);
        EthiopianDrinkType drinkType = drinkTypes[choice - 1];

        // 3) Create drink
        Drink drink = new Drink("Drink", size, drinkType.getDisplayName());

        System.out.printf("✅  Added drink: %s (%s) - %.2f%n",
                drinkType.getDisplayName(),
                size.getDisplayName(),
                drink.calculatePrice());

        return drink;
    }

    // ---------- helpers ----------

    private Size promptForSize() {
        System.out.println("📏  Choose size:");
        Size[] sizes = Size.values();
        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("%d) %s%n", i + 1, sizes[i].getDisplayName());
        }
        System.out.print("👉  Enter choice: ");
        int choice = readIntInRange(1, sizes.length);
        return sizes[choice - 1];
    }

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
