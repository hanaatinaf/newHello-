package com.pluralsight.Classes;

import java.io.*;
import java.time.format.DateTimeFormatter;

public class ReceiptWriter  {
    private String outPutPath;

    public ReceiptWriter(String outPutPath) {
        this.outPutPath = outPutPath;
    }


   public  boolean save(Order order) throws IOException {
        // create directory if it doesn't exist
       File directory = new File(outPutPath);
       if (!directory.exists()){
           boolean created = directory.mkdirs();
           if (!created){
               System.out.println("Could not create directory");
               return  false;

           }
       }

        String fileName = generateFileName(order);

        String fullPath = outPutPath + "/" + fileName;

        try (FileWriter writer = new FileWriter(fullPath)){
            writer.write(order.generateReceipt());
            return  true;


        }catch (IOException e){
            System.out.println("Error saving receipt: " + e.getMessage());
            return  false;
        }


   }
   public String generateFileName(Order order){

       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

       String timestamp = order.getDateAndTime().format((formatter));

        return  timestamp + ".txt";
        //return  "Receipt " + order.getId() + ".txt";


   }
}
