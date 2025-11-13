package com.pluralsight.Classes;

import com.pluralsight.Abstract.Product;
import com.pluralsight.Enum.*;
import com.pluralsight.Classes.*;
import com.pluralsight.Classes.Drink.*;
import com.pluralsight.Classes.Toppings.*;
import com.pluralsight.Classes.Sides.*;
import java.util.*;

public class UIController {

    private Order currentOrder;
    private final ReceiptWriter receiptWriter;
    private final Scanner scanner;

    public UIController() {
        this.scanner = new Scanner(System.in);
        // Receipts will be saved into a "receipts" folder (created if missing)
        this.receiptWriter = new ReceiptWriter("receipts");
    }

    // ---------------------------------------------------------
    // Entry point for the UI
    // ---------------------------------------------------------

    // Nice banner for Abyssinia restaurant
    private void printRestaurantBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║          የአቢሲኒያ ኢትዮጵያዊ ምግብ ቤት           ║");
        System.out.println("║              Abyssinia  Ethiopian Restaurant ║ ");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("     ሰላም እንኳን በደህና መጡ!  Welcome! ✨");
        System.out.println();
    }

    public void start() {
        printRestaurantBanner();

        // Keep showing the home screen until the user exits the app.
        while (true) {
            homeScreen();
        }
    }

    // ---------------------------------------------------------
    // Home Screen
    // ---------------------------------------------------------

    public void homeScreen() {
        System.out.println("🏠  ዋና መስኮት | HOME SCREEN");
        System.out.println("────────────────────────────────────────");
        System.out.println("1) 🧾  አዲስ ትዕዛዝ  (New Order)");
        System.out.println("0) 🚪  መውጫ        (Exit)");
        System.out.println("────────────────────────────────────────");
        System.out.print("👉  እባክዎ ምርጫዎን ያስገቡ | Choose an option: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                currentOrder = new Order();
                System.out.println();
                System.out.println("🧾 አዲስ ትዕዛዝ ተጀምሯል | New order started.");
                System.out.println("   Order ID: " + currentOrder.getId());
                orderScreen();
                break;
            case 0:
                System.out.println();
                System.out.println("🙏  አመሰግናለሁ ወደ Abyssinia ስለመጡ!  Goodbye!");
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println("⚠️  የተሳሳተ ምርጫ | Invalid option. Please try again.");
        }
    }

    // ---------------------------------------------------------
    // Order Screen
    // ---------------------------------------------------------

    public void orderScreen() {
        boolean inOrder = true;

        while (inOrder) {
            System.out.println();
            System.out.println("🧺  የትዕዛዝ መስኮት | ORDER SCREEN");
            System.out.println("────────────────────────────────────────");
            System.out.println("1) 🍽️  ዋና ምግብ (Add Ethiopian Food Item)");
            System.out.println("2) 🥤  መጠጥ     (Add Drink)");
            System.out.println("3) 🥗  የጎን ምግብ (Add Side)");
            System.out.println("4) ✅  መጨረሻ ክፍያ (Checkout)");
            System.out.println("0) ❌  ሰርዝ      (Cancel Order)");
            System.out.println("────────────────────────────────────────");
            System.out.print("👉  ምን ማድረግ ትፈልጋለህ? | Choose an option: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    addItemScreen();
                    break;
                case 2:
                    addDrinkScreen();
                    break;
                case 3:
                    addSideScreen();
                    break;
                case 4:
                    checkoutScreen();
                    inOrder = false;   // go back to home screen after checkout
                    break;
                case 0:
                    System.out.println("❌  ትዕዛዙ ተሰርዟል | Order cancelled. Returning to home.");
                    currentOrder = null;
                    inOrder = false;
                    break;
                default:
                    System.out.println("⚠️  የተሳሳተ ምርጫ | Invalid option. Please try again.");
            }
        }
    }

    // ---------------------------------------------------------
    // Add Ethiopian Food Item
    // ---------------------------------------------------------

    public void addItemScreen() {
        System.out.println();
        System.out.println("🍽️  ዋና ምግብ መጨመሪያ | ADD ETHIOPIAN FOOD ITEM");
        System.out.println("────────────────────────────────────────");

        // 1) Select size
        Size size = promptForSize();

        // 2) Select food type from FoodType enum
        FoodType type = promptForItemType();

        // 3) Create the item (customer or signature)
        EthiopianFoodItem item = new EthiopianFoodItem(type, size);

        // 4) Ask if specialized
        System.out.print("⭐SPECIAL ልታደርጉት ትፈልጋለህ? | Would you like to make it SPECIAL? (y/n): ");
        String specializedInput = scanner.next().trim().toLowerCase();
        item.setSpecialized(specializedInput.startsWith("y"));

        if (item.isSpecialized()) {
            System.out.println("✅  Special customization added (+extra charge based on size).");
        } else {
            System.out.println("👌  Regular item selected (no special customization).");
        }

        // 5) Prompt for toppings
        promptForToppings(item);

        // 6) Add to current order
        currentOrder.addProduct(item);
        System.out.printf("✅  %s ታክሏል | Added to order. Current total: %.2f%n",
                item.getName(), currentOrder.calculateTotal());

        System.out.println("↩️  ወደ የትዕዛዝ መስኮት በመመለስ... | Returning to order screen...");
    }

    // ---------------------------------------------------------
    // Add Drink
    // ---------------------------------------------------------

    public void addDrinkScreen() {
        System.out.println();
        System.out.println("🥤  መጠጥ መጨመሪያ | ADD DRINK");
        System.out.println("────────────────────────────────────────");

        // 1) Choose size
        Size size = promptForSize();

        // 2) Show available Ethiopian drinks (your enum)
        System.out.println("እባክዎ መጠጥ ይምረጡ | Choose a drink type:");
        EthiopianDrinkType[] drinkTypes = EthiopianDrinkType.values();
        for (int i = 0; i < drinkTypes.length; i++) {
            System.out.printf("%d) %s%n", i + 1, drinkTypes[i].getDisplayName());
        }
        System.out.print("👉  Enter choice: ");
        int choice = readIntInRange(1, drinkTypes.length);
        EthiopianDrinkType drinkType = drinkTypes[choice - 1];

        // 3) Create drink and add to order
        Drink drink = new Drink("Drink", size, drinkType.getDisplayName());
        currentOrder.addProduct(drink);

        System.out.printf("✅  Added drink: %s (%s) - %.2f%n",
                drinkType.getDisplayName(),
                size.getDisplayName(),
                drink.calculatePrice());
    }

    // ---------------------------------------------------------
    // Add Side
    // ---------------------------------------------------------

    public void addSideScreen() {
        System.out.println();
        System.out.println("🥗  የጎን ምግብ መጨመሪያ | ADD SIDE");
        System.out.println("────────────────────────────────────────");

        // Even though size doesn't change price for sides, we keep it for consistency
        Size size = promptForSize();

        System.out.println("እባክዎ የጎን ምግብ ይምረጡ | Choose a side type:");
        EthiopianSideType[] sideTypes = EthiopianSideType.values();
        for (int i = 0; i < sideTypes.length; i++) {
            System.out.printf("%d) %s%n", i + 1, sideTypes[i].getDisplayName());
        }
        System.out.print("👉  Enter choice: ");
        int choice = readIntInRange(1, sideTypes.length);
        EthiopianSideType sideType = sideTypes[choice - 1];

        Side side = new Side(sideType.getDisplayName(), size);
        currentOrder.addProduct(side);

        System.out.printf("✅  Added side: %s - %.2f%n",
                sideType.getDisplayName(),
                side.calculatePrice());
    }

    // ---------------------------------------------------------
    // Checkout
    // ---------------------------------------------------------

    public void checkoutScreen() {
        System.out.println();
        System.out.println("✅  መጨረሻ ክፍያ | CHECKOUT");
        System.out.println("────────────────────────────────────────");

        if (currentOrder == null || currentOrder.getProducts().isEmpty()) {
            System.out.println("🕳️  ባዶ ትዕዛዝ ነው | Your order is empty. Nothing to checkout.");
            return;
        }

        // 1) Show order details on screen
        displayOrderDetails();

        // 2) Confirm
        System.out.print("Confirm order? (1 = Yes, 0 = No): ");
        int choice = readInt();

        if (choice == 1) {
            currentOrder.completeOrder();

            boolean saved = receiptWriter.save(currentOrder);
            if (saved) {
                System.out.println("🎉  ትዕዛዙ ተጠናቋል እና ሬሲት ተመዝግቧል | Order completed and receipt saved.");
            } else {
                System.out.println("⚠️  Order completed, but there was an error saving the receipt.");
            }

            // After checkout, clear currentOrder so user must start a new one
            currentOrder = null;
        } else {
            System.out.println("↩️  Checkout cancelled. Returning to order screen.");
        }
    }

    // ---------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------


     // Prints the current order's receipt text to the console.

    public void displayOrderDetails() {
        if (currentOrder == null) {
            System.out.println("No active order.");
            return;
        }

        String receipt = currentOrder.generateReceipt();
        System.out.println(receipt);

        // Extra detailed view using instanceof (for your rubric)
        System.out.println("🔍  ዝርዝር መመልከቻ | Detailed View");
        if (currentOrder.getProducts().isEmpty()) {
            System.out.println("   (No items in order)");
            return;
        }

        for (Product product : currentOrder.getProducts()) {
            System.out.println(" - " + product.getName());

            // Use instanceof to check if this is an EthiopianFoodItem
            if (product instanceof EthiopianFoodItem) {
                EthiopianFoodItem food = (EthiopianFoodItem) product;

                // Show toppings if there are any
                if (food.getToppings() != null && !food.getToppings().isEmpty()) {
                    System.out.println("   Toppings:");
                    food.getToppings().forEach(t ->
                            System.out.println("     • " + t.getName()));
                } else {
                    System.out.println("   (No toppings)");
                }

                // Show if it is special
                if (food.isSpecialized()) {
                    System.out.println("   SPECIAL ITEM ✅");
                } else {
                    System.out.println("   Regular item");
                }
            }
        }

        System.out.printf("👉  Total: %.2f%n", currentOrder.calculateTotal());
    }


     // Asks the user to choose a size from the Size enum.

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

    //Asks the user to choose an Ethiopian food type (FoodType enum).

    private FoodType promptForItemType() {
        System.out.println("🍽️  እባክዎ ዋና ምግብ ይምረጡ | Choose Ethiopian food type:");
        FoodType[] types = FoodType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.printf("%d) %s%n", i + 1, types[i].getDisplayName());
        }
        System.out.print("👉  Enter choice: ");
        int choice = readIntInRange(1, types.length);
        return types[choice - 1];
    }

    /**
     * Simple version of toppings selection.
     * Right now we just let the user choose a few basic toppings by name.
     * You can expand this later to display a fixed list (meats, cheeses, etc.).
     */
    private void promptForToppings(EthiopianFoodItem item) {
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
                    // Show regular topping list
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
                    // Show premium topping list
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

    // --- Input helpers ---

    private int readInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                return value;
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
