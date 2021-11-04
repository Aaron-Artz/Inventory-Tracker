package InventorySystem.Model;

import InventorySystem.Model.Part;
import InventorySystem.Model.InHouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 *
 * Aaron Artz
 * May 1, 2020
 * WGU C482 Final
 *
 */


public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;


    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static boolean removeProduct(int productID) {
        for (Product p : allProducts){
            if (p.getProductID() == productID) {
                allProducts.remove(p);
                return true;
            }
        }
        return false;
    }
    public static Product lookupProduct(String searchedProd) {
        for (Product p : allProducts) {
            if (p.getName().contains(searchedProd)) {
                return p;
            }
        }
        return null;
    }

    public static Part lookupPart(String partName) {
        for(Part p : allParts) {
            if(p.getName().contains(partName))
                return p;
        }
        return null;
    }

    public static void updateProduct(int productIndex, Product product) {
        allProducts.set(productIndex, product);
    }

    public static void addPart(Part part) {
        allParts.addAll(part);
    }

    public static boolean deletePart(int partID) {
        for (Part p : allParts) {
            if (p.getPartID() == partID) {
                allParts.remove(p);
                return true;
            }
        }
        return false;
    }


    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void updatePart(int partIndex, Part part) {
        allParts.set(partIndex, part);
    }

    public static ObservableList<Part> getParts() { return allParts; }


    public static ObservableList<Product> getProducts() {
        return allProducts;
    }

    public static int getProductIDCount() {
        productID++;
        return productID;
    }


}
