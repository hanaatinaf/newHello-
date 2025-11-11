package com.pluralsight.Classes;

import com.pluralsight.Abstract.Product;
import com.pluralsight.Enum.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;


public class Order  {
    private String Id;
    private LocalDateTime dateAndTime;
    private List<Product> products = new ArrayList<>();
    private boolean isCompleted;

    // contractores
    public Order() {
        // to get Unique id automaticall instade of to ask the user to create ID manually
        this.Id =  UUID.randomUUID().toString();
        this.dateAndTime = LocalDateTime.now();
        this.isCompleted = false;
    }

    // contract if we want to supplay id and date
    public Order(String id, LocalDateTime dateAndTime, List<Product> products, boolean isCompleted) {
        Id = id;
        this.dateAndTime = dateAndTime;
        this.isCompleted = false;
    }

    // getter and setter

    public String getId() { return Id;}

    public void setId(String id) { Id = id; }


    public LocalDateTime getDateAndTime() { return dateAndTime; }

    public void setDateAndTime(LocalDateTime dateAndTime) { this.dateAndTime = dateAndTime; }

    public boolean isCompleted() { return isCompleted;}

    public void setCompleted(boolean completed) {
        isCompleted = completed; }

    // methods

  public  void addProduct(Product product){
        products.add(product); }
  public  void removeProduct(Product product){
        products.remove(product); }

  public  List<Product> getProducts(){
      return List.of(); };

    public  double calculateTotal(){
        return  products.stream().mapToDouble(Product::calculatePrice).sum();

    }
    public  void completeOrder(){
        this.isCompleted = true;
    }
    public String generateReceipt(){

        return "";
    }


}
