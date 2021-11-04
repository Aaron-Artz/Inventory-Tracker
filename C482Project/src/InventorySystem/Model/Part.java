package InventorySystem.Model;

import javafx.beans.property.*;
import InventorySystem.Model.OutSourced;
import InventorySystem.Model.InHouse;

/*
 *
 * Aaron Artz
 * May 1, 2020
 * WGU C482 Final
 *
 */



public abstract class Part {

    private int partID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;



    public Part(int partID, String name, double price, int inStock, int min, int max) {
        this.partID = partID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }
    public int getPartID() { return partID; }

    public void setPartID(int partID) { this.partID = partID; }

    public String getName() {return name;}

    public void setName(String name) { this.name = name; }

    public double getPrice() {return price;}

    public void setPrice(double price) { this.price = price;}

    public int getInStock() { return inStock;}

    public void setInStock(int inStock) {this.inStock = inStock;}

    public int getMin() { return min; }

    public void setMin(int min) {this.min = min;}

    public int getMax() { return max; }

    public void setMax(int max) {this.max = max;}




    public static Part searchPart(String lookName) {

        for (Part p:Inventory.getParts()) {
            if(p.getName().contains(lookName) || new Integer (p.getPartID()).toString().equals(lookName)) return p;
        }
        return null;
    }




    public static String invalidPartWarning(int inStock, double price, int max, int min, String invalidPartText) {
        if (inStock < 1) {
            invalidPartText = invalidPartText + "\nThe Inventory cannot be less than 1.\n ";
        }
        if (price <= 0) {
            invalidPartText = invalidPartText + "\nThe Price must be greater than $0.\n ";
        }
        if (max < min) {
            invalidPartText = invalidPartText + "\nThe Maximum stock must be greater than the Minimum stock.\n ";
        }
        if (inStock > max) {
            invalidPartText = invalidPartText + "\nThe Inventory must be less than or equal to the Maximum stock.\n ";
        }
        if (inStock < min) {
            invalidPartText = invalidPartText + "\nThe Inventory must be greater than or equal to the Minimum stock.\n ";
        }
        return invalidPartText;
    }
}
