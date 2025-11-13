package com.pluralsight.Classes;


import java.io.*;
import java.time.format.DateTimeFormatter;

/**
 * Handles writing order receipts to text files using BufferedWriter.
 *
 * Each order is saved in a separate file named:
 *   yyyyMMdd-HHmmss.txt
 * inside the outputPath folder.
 */
public class ReceiptWriter {

    private final String outputPath;

    // Formatter for the file name (same pattern as the assignment)
    private static final DateTimeFormatter FILE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    /**
     * @param outputPath directory where receipt files will be stored
     *                   (e.g., "receipts")
     */
    public ReceiptWriter(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * Generates the receipt file name for a given order.
     * Uses the order's date/time in the format: yyyyMMdd-HHmmss.txt
     */
    public String generateFileName(Order order) {
        String timestamp = order.getDateTime().format(FILE_FORMATTER);
        return timestamp + ".txt";
    }

    /**
     * Saves the receipt for the given order to a text file
     * using BufferedWriter.
     *
     * @return true if the file was written successfully, false otherwise.
     */
    public boolean save(Order order) {
        // Ensure the directory exists
        File directory = new File(outputPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                System.err.println("Could not create receipts directory: " + outputPath);
                return false;
            }
        }

        String fileName = generateFileName(order);
        File receiptFile = new File(directory, fileName);

        // Try-with-resources to automatically close the writer
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(receiptFile))) {
            String receiptText = order.generateReceipt();
            writer.write(receiptText);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing receipt file: " + e.getMessage());
            return false;
        }
    }
}
