package InventorySystem;


import InventorySystem.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Controller/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Managment System");
        primaryStage.setScene(new Scene(root, 1100, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {



        Part part1 = new InHouse(1, "Bell", 5.0, 3, 1, 5, 1);
        Inventory.addPart(part1);

        Part part2 = new InHouse(2, "Seat", 15.99, 4, 2, 10, 10);
        Inventory.addPart(part2);

        Part part3 = new OutSourced(3, "Tire", 8.99, 12, 4, 20, "Terry's Tire Palace");
        Inventory.addPart(part3);

        Part part4 = new OutSourced(4, "Break", 14.99, 7, 4, 12, "Benjamin's Break Busters");
        Inventory.addPart(part4);

        Part part5 = new OutSourced(5, "Red Paint", 2.99, 5, 1, 10, "Color Me Impressed");
        Inventory.addPart(part5);

        Part part6 = new OutSourced(6, "Blue Paint", 2.99, 5, 1, 10, "Color Me Impressed");
        Inventory.addPart(part6);

        Part part7 = new OutSourced(7, "Yellow Paint", 2.99, 5, 1, 10, "Color my Impressed");
        Inventory.addPart(part7);

        Product product1 = new Product(1, "Red Bike", 79.99 , 5, 5, 15);
        Inventory.addProduct(product1);

        Product product2 = new Product(2, "Blue Bike", 79.99, 7, 5, 15);
        Inventory.addProduct(product2);

        Product product3 = new Product(3, "Yellow Bike", 79.99, 12, 5, 15);
        Inventory.addProduct(product3);

        Product product4 = new Product(4, "Unpainted Bike", 69.99, 5, 2, 10);
        Inventory.addProduct(product4);

        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);
        product1.addAssociatedPart(part3);
        product1.addAssociatedPart(part4);
        product1.addAssociatedPart(part5);

        product2.addAssociatedPart(part1);
        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part3);
        product2.addAssociatedPart(part3);
        product2.addAssociatedPart(part4);
        product2.addAssociatedPart(part6);

        product3.addAssociatedPart(part1);
        product3.addAssociatedPart(part2);
        product3.addAssociatedPart(part3);
        product3.addAssociatedPart(part3);
        product3.addAssociatedPart(part4);
        product3.addAssociatedPart(part7);

        product4.addAssociatedPart(part1);
        product4.addAssociatedPart(part2);
        product4.addAssociatedPart(part3);
        product4.addAssociatedPart(part3);
        product4.addAssociatedPart(part4);

        launch(args);
    }
/*
    public static void main(String[] args) {
        launch(args);
    }
    */
}
