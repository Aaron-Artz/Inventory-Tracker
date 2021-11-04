package InventorySystem.Model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

/*
 *
 * Aaron Artz
 * May 1, 2020
 * WGU C482 Final
 *
 */


public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int productID, String name, double price, int stock, int min, int max) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public void addAssociatedPart (Part part) { this.associatedParts.add(part); }



    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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



    public  ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }



// SetAllAssocParts previouse this.associatedParts.assAll()
    public void setAllAssociatedParts (ObservableList<Part> associatedParts) {
        this.associatedParts.addAll();
    }








    public Part lookupAssociatedPart(int partID) {
        return associatedParts.get(partID);
    }

    public boolean deleteAssociatedPart(int partID) {
        for (Part p : associatedParts) {
            if (p.getPartID() == partID) {
                associatedParts.remove(p);
                return true;
            }
        }
        return false;
    }

    public static Product searchProduct(String lookName) {

        for (Product p:Inventory.getProducts()) {
            if(p.getName().contains(lookName) || new Integer (p.getProductID()).toString().equals(lookName)) return p;
        }
        return null;
    }

    public static String getProductValidation(int inStock, double price, int max, int min, String invaledWarning) {
        if (inStock < 1) {
            invaledWarning = invaledWarning + "\nThe Inventory cannot be less than 1. ";
        }
        if (price <= 0) {
            invaledWarning = invaledWarning + "\nThe Price must be greater than $0. ";
        }
        if (max < min) {
            invaledWarning = invaledWarning + "\nThe Maximum stock must be greater than the Minimum stock. ";
        }
        if (inStock > max) {
            invaledWarning = invaledWarning + "\nThe Inventory must be less than or equal to the Maximum stock. ";
        }
        if (inStock < min) {
            invaledWarning = invaledWarning + "\nThe Inventory must be greater than or equal to the Minimum stock. ";
        }
        return invaledWarning;
    }
}
