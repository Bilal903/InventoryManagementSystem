package com.example.inventory_manage_application.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Products class represents a product with various characteristics,
 such as an ID, a name, a price, and a list of associated parts.
 * The class has private fields for these characteristics and provides getter and setter methods for them.
 * The associatedParts field is of type ObservableList<Parts> and allows you to track changes to the list of associated parts.
 * The class has two constructors: one that takes six parameters to initialize the fields, and another that calls the first constructor with default values.
 * The class also has a addAssociatedPart method to add a given list of parts to the associatedParts field and a getAllAssociatedParts method to retrieve the associatedParts field.
 * */

public class Products {

    private ObservableList<Parts> associatedParts = FXCollections.observableArrayList();
    private int productID, stock, min, max;
    private String name;
    private double productPrice;

    public Products(int productID, int stock, int min, int max, String name, double productPrice) {
        this.productID = productID;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.name = name;
        this.productPrice = productPrice;
    }

    public Products() {
        this(0, 0, 0, 0, null, 0.00);
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void addAssociatedPart(ObservableList<Parts> part){
        this.associatedParts.addAll(part);
    }

    public ObservableList<Parts> getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ObservableList<Parts> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public ObservableList<Parts> getAllAssociatedParts(){
        return associatedParts;
    }
}
