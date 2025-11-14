package com.pluralsight.Classes;

import com.pluralsight.Abstract.*;
import com.pluralsight.Classes.Toppings.ToppingMenu;
import com.pluralsight.Enum.FoodType;
import com.pluralsight.Enum.Size;

import java.util.InputMismatchException;
import java.util.Scanner;

// Handles all Ethiopian food (main plate) flows: signature/regular plate, custom plate
public class FoodMenu {
    private final Scanner scanner;
    private final ToppingMenu toppingMenu;

    public FoodMenu(Scanner scanner, ToppingMenu toppingMenu) {
        this.scanner = scanner;
        this.toppingMenu = toppingMenu;
    }

    // Entry point when the user chooses "Add Ethiopian Food Item" from UIController.
    // Shows regular/custom menu and adds the chosen item to the given Order.
    public void addEthiopianFoodItemToOrder(Order order) {
        System.out.println();
        System.out.println("🍽️ ADD ETHIOPIAN FOOD ITEM");
        System.out.println("=======================================");
        System.out.println("1) 🌟 Signature Ethiopian Plate (pre-built combo)");
        System.out.println("2) 🧩 Custom Ethiopian Plate (build your own)");
        System.out.println("0) ↩️ Back ");
        System.out.print("👉  Choose an option: ");

        int choice = readIntInRange(0, 2);

        switch (choice) {
            case 1:
                handleSignaturePlate(order);
                break;
            case 2:
                handleCustomPlate(order);
                break;
            case 0:
                System.out.println("↩️   Returning to order screen...");
                break;
            default:
                System.out.println("⚠️  Invalid option.");
        }
    }

    // 🔹 New: Signature / Regular Plate uses your createXxx() templates
    private void handleSignaturePlate(Order order) {
        // 1) Choose size
        Size size = promptForSize();

        // 2) Choose which signature plate, showing descriptions
        FoodType type = promptForRegularFoodTypeWithDescriptions();

        // 3) Create the item using your signature factories
        EthiopianFoodItem item = createSignatureItem(type, size);

        // 4) Ask if the customer wants to customize this signature plate
        System.out.print("🔧  Would you like to add extra toppings to this signature plate? (y/n): ");
        String customizeInput = scanner.next().trim().toLowerCase();

        if (customizeInput.startsWith("y")) {
            item.setSpecialized(true);
            toppingMenu.promptForToppings(item);   // only adds more on top of defaults
        } else {
            item.setSpecialized(false);
            System.out.println("👌  Signature plate selected (no extra customization).");
        }

        // 5) Add to order
        order.addProduct(item);
        System.out.printf("✅  Signature %s  Added to order. Current total: %.2f%n",
                item.getName(), order.calculateTotal());

        System.out.println("↩️ Returning to order screen...");
    }

    // Helper: map FoodType → your static factory methods
    private EthiopianFoodItem createSignatureItem(FoodType type, Size size) {
        switch (type) {
            case BEYAYNETU:
                return EthiopianFoodItem.createBeyaynetu(size);
            case TIBS_PLATE:
                return EthiopianFoodItem.createTibsPlate(size);
            case KITFO_PLATE:
                return EthiopianFoodItem.createKitfoPlate(size);
            case INJERA_COMBO:
                return EthiopianFoodItem.createInjeraCombo(size);
            default:
                // Fallback: simple item with no defaults
                return new EthiopianFoodItem(type, size);
        }
    }

    // 🔹 Custom build-your-own plate (your original custom flow)
    private void handleCustomPlate(Order order) {
        // 1) Choose size
        Size size = promptForSize();

        // 2) Choose a base type for the custom plate
        System.out.println("🧩  Choose a base for your custom plate:");
        FoodType baseType = promptForRegularFoodTypeWithDescriptions();

        // 3) Create the item as "specialized"
        EthiopianFoodItem item = new EthiopianFoodItem(baseType, size);
        item.setSpecialized(true);   // custom plate is always special

        System.out.println("🔧 Now choose toppings for your custom plate:");
        toppingMenu.promptForToppings(item);

        // 4) Add to order
        order.addProduct(item);
        System.out.printf("✅  Custom %s  Added to order. Current total: %.2f%n",
                item.getName(), order.calculateTotal());

        System.out.println("↩️ Returning to order screen:");
    }

    // helpers just for this menu
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

    private FoodType promptForRegularFoodTypeWithDescriptions() {
        System.out.println("🍛  መደበኛ የኢትዮጵያ ምግቦች | Regular Ethiopian Plates:");
        FoodType[] types = FoodType.values();

        for (int i = 0; i < types.length; i++) {
            FoodType type = types[i];
            System.out.printf("%d) %s%n", i + 1, type.getDisplayName());

            String name = type.getDisplayName().toLowerCase();
            if (name.contains("tibs")) {
                System.out.println("   - Sautéed beef with onions and peppers.");
            } else if (name.contains("kitfo")) {
                System.out.println("   - Minced seasoned beef with niter kibbeh & ayib.");
            } else if (name.contains("doro")) {
                System.out.println("   - Spicy chicken stew with boiled egg, served on injera.");
            } else if (name.contains("beyaynetu") || name.contains("veg")) {
                System.out.println("   - Mixed vegetarian platter on injera.");
            } else if (name.contains("shiro")) {
                System.out.println("   - Chickpea stew served with injera.");
            } else {
                System.out.println("   - Traditional Ethiopian dish.");
            }
        }
        System.out.print("👉  Choose a plate: ");
        int choice = readIntInRange(1, types.length);
        return types[choice - 1];
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
