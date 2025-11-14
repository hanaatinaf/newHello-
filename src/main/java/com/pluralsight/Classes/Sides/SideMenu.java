package com.pluralsight.Classes.Sides;

import com.pluralsight.Classes.Sides.Side;
import com.pluralsight.Enum.EthiopianSideType;
import com.pluralsight.Enum.Size;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Handles side selection and Side creation.
 */
public class SideMenu {

    private final Scanner scanner;

    public SideMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Shows side menu and returns the chosen Side.
     */
    public Side handleAddSide() {
        System.out.println();
        System.out.println("🥗  የጎን ምግብ መጨመሪያ | ADD SIDE");
        System.out.println("────────────────────────────────────────");

        // size (for consistency)
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

        System.out.printf("✅  Added side: %s - %.2f%n",
                sideType.getDisplayName(),
                side.calculatePrice());

        return side;
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
