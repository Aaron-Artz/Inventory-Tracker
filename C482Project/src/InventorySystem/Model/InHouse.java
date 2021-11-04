package InventorySystem.Model;


import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.omg.CORBA.INTERNAL;

/*
 *
 * Aaron Artz
 * May 1, 2020
 * WGU C482 Final
 *
 */


public class InHouse extends Part {

    private int machineID;

    public InHouse(int partID, String name, double price, int inStock, int min, int max, int machineID) {
        super(partID, name, price, inStock, min, max);
        this.machineID = machineID;
    }


    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}





