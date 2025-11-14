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

    // New menu helpers
    private final ToppingMenu toppingMenu;
    private final FoodMenu foodMenu;
    private final DrinkMenu drinkMenu;
    private final SideMenu sideMenu;

    public UIController() {
        this.scanner = new Scanner(System.in);
        this.receiptWriter = new ReceiptWriter("receipts");

        // Menus share the same Scanner
        this.toppingMenu = new ToppingMenu(scanner);
        this.foodMenu = new FoodMenu(scanner, toppingMenu);
        this.drinkMenu = new DrinkMenu(scanner);
        this.sideMenu = new SideMenu(scanner);
    }

    // ---------------------------------------------------------
    // Entry point for the UI
    // ---------------------------------------------------------

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
                System.out.println("🧾  አዲስ ትዕዛዝ ተጀምሯል | New order started.");
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
                    // Delegate to FoodMenu
                    foodMenu.addEthiopianFoodItemToOrder(currentOrder);
                    break;
                case 2:
                    // Delegate to DrinkMenu
                    Drink drink = drinkMenu.handleAddDrink();
                    currentOrder.addProduct(drink);
                    break;
                case 3:
                    // Delegate to SideMenu
                    Side side = sideMenu.handleAddSide();
                    currentOrder.addProduct(side);
                    break;
                case 4:
                    checkoutScreen();
                    inOrder = false;
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

            currentOrder = null;
        } else {
            System.out.println("↩️  Checkout cancelled. Returning to order screen.");
        }
    }

    // ---------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------

    public void displayOrderDetails() {
        if (currentOrder == null) {
            System.out.println("No active order.");
            return;
        }

        String receipt = currentOrder.generateReceipt();
        System.out.println(receipt);

        System.out.println("🔍  ዝርዝር መመልከቻ | Detailed View");
        if (currentOrder.getProducts().isEmpty()) {
            System.out.println("   (No items in order)");
            return;
        }

        for (Product product : currentOrder.getProducts()) {
            System.out.println(" - " + product.getName());

            if (product instanceof EthiopianFoodItem) {
                EthiopianFoodItem food = (EthiopianFoodItem) product;

                if (food.getToppings() != null && !food.getToppings().isEmpty()) {
                    System.out.println("   Toppings:");
                    food.getToppings().forEach(t ->
                            System.out.println("     • " + t.getName()));
                } else {
                    System.out.println("   (No toppings)");
                }

                if (food.isSpecialized()) {
                    System.out.println("   SPECIAL ITEM ✅");
                } else {
                    System.out.println("   Regular item");
                }
            }
        }

        System.out.printf("👉  Total: %.2f%n", currentOrder.calculateTotal());
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
}
