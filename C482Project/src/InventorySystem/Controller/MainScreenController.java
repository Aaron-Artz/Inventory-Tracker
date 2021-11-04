package InventorySystem.Controller;

import InventorySystem.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static InventorySystem.Model.Inventory.*;

/*
 *
 * Aaron Artz
 * May 1, 2020
 * WGU C482 Final
 *
 */


public class MainScreenController implements Initializable {

    @FXML private AnchorPane mainScrn;

    @FXML private Button extButton;

    @FXML private Button partSearchButton;

    @FXML private Button partAddButton;

    @FXML private Button partModifyButton;

    @FXML private Button partDeleteButton;

    @FXML private Button productSearchButton;

    @FXML private Button productAddButton;

    @FXML private Button productModifyButton;

    @FXML private Button productDeleteButton;

    @FXML private TextField partSearchField;

    @FXML private TextField productSearchField;

    @FXML private TableView<Part> viewParts;

    @FXML private TableColumn<Part, Integer> partIDCol;

    @FXML private TableColumn<Part, String> partNameCol;

    @FXML private TableColumn<Part, Integer> partInStockCol;

    @FXML private TableColumn<Part, Double> partPriceCol;

    @FXML private TableColumn<Product, Integer> productIDCol;

    @FXML private TableColumn<Product, String> productNameCol;

    @FXML private TableColumn<Product, Integer> productInventoryCol;

    @FXML private TableColumn<Product, Double> productPriceCol;

    @FXML private TableView<Product> viewProducts;

    private static Product selectedProduct;

    private static int selectedProductIndex;

    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }

    public static ObservableList<Part> selectedAssociatedParts = FXCollections.observableArrayList();







// Exit Button With Prompt

    public void exitButtonAction (ActionEvent aExitButton) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Inventory Managment System");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Stage winMainScreen = (Stage)((Node)aExitButton.getSource()).getScene().getWindow();
            winMainScreen.close();
        }
        else {
            alert.close();
        }

    }


    //Delete Part Button
    public void deletePartAction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Part Confirmation");
        alert.setHeaderText("You are about to remove a Part!");
        alert.setContentText("Press OK to Remove the Part. \nPress Cancel to return to the main screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            ObservableList<Part> selectedRows, allParts;
            allParts = viewParts.getItems();
            selectedRows = viewParts.getSelectionModel().getSelectedItems();
            for (Part part : selectedRows) {
                allParts.remove(part);
            }
        }
        else {
            alert.close();
        }
    }

    public void deleteProductAction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Product Confirmation");
        alert.setHeaderText("You are about to remove a Product!");
        alert.setContentText("Press OK to Remove the Product. \nPress Cancel to return to the main screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            ObservableList<Product> selectedRows, allProducts;
            allProducts = viewProducts.getItems();
            selectedRows = viewProducts.getSelectionModel().getSelectedItems();
            for (Product product : selectedRows) {
                allProducts.remove(product);
            }
        }
        else {
            alert.close();
        }
    }

    // Change to AddPart Scene
@FXML
    public void partAddButtonAction (ActionEvent aPartAddButton) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddPartScreen.fxml"));
        Parent addPartScreen = (Parent) fxmlLoader.load();
        Scene addPartScene = new Scene(addPartScreen);
        Stage addPartWin = (Stage)((Node)aPartAddButton.getSource()).getScene().getWindow();
        addPartWin.setScene(addPartScene);
        addPartWin.setTitle("Inventory Managment System (Add Part)");
        addPartWin.show();
    }

    // Change to AddProduct Scene

    @FXML
    void productAddButtonAction(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    // Change to ModifyPart Scene

    @FXML
    void partModifyButtonAction(ActionEvent event) throws IOException {
        Part part=viewParts.getSelectionModel().getSelectedItem();
        int index = viewParts.getSelectionModel().getSelectedIndex();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modify Product Warning");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("You must select a product in order to modify.");
            alert.showAndWait();
        }
        else {
            Stage stage;
            Parent root;
            stage = (Stage) partModifyButton.getScene().getWindow();
            //load up OTHER FXML document
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "ModifyPartScreen.fxml"));

            root = loader.load();
            ModifyPartController controller = loader.getController();
            if (part != null) {
                controller.setPart(part, index);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    // Change to Modift Product Scenewa

    public void productModifyButtonAction (ActionEvent aProductModifyButton) throws Exception {
        selectedProduct = viewProducts.getSelectionModel().getSelectedItem();
        selectedProductIndex = getProducts().indexOf(selectedProduct);
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modify Product Warning");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("You must select a product in order to modify.");
            alert.showAndWait();
        }
        else {
            Stage stage;
            Parent parent;
            stage = (Stage) productModifyButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyProductScreen.fxml"));
            parent = fxmlLoader.load();
            ModifyProductController controller = fxmlLoader.getController();
            Product product = viewProducts.getSelectionModel().getSelectedItem();
            int index = viewProducts.getSelectionModel().getSelectedIndex();
            if (product != null) {
                controller.setProduct(product, index);
            }
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void updatePartsTV() {
        viewParts.setItems(getParts());
    }

@FXML
public void searchPartButtonAction(ActionEvent event) {
    String searchPartNameString = partSearchField.getText();
    ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
    if (searchPartNameString.equals("")) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Search Warning");
        alert.setHeaderText("Your search did not match any results.");
        alert.setContentText("You did not enter a product to search for.");
        alert.showAndWait();
    }
    else {
        boolean found = false;
        try {
            Part searchPart = Inventory.lookupPart(searchPartNameString);

            if (searchPart == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Search Warning");
                alert.setHeaderText("There were no parts found.");
                alert.setContentText("The item you searched for does not match any results.");
                alert.showAndWait();
            } else {
                for (Part p : getParts()) {
                    if (p.getName().contains(searchPartNameString)) {
                        found = true;
                        filteredPartsList.add(p);
                        viewParts.setItems(filteredPartsList);
                    }
                }
                if (found == false) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Product Search Warning");
                    alert.setHeaderText("There were no parts found!");
                    alert.setContentText("The search term entered does not match any product names!");
                    alert.showAndWait();
                }

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}



@FXML
public void searchProductButtonAction(ActionEvent event) {
    String searchProductNameString = productSearchField.getText();
    ObservableList<Product> filteredProductsList = FXCollections.observableArrayList();
    if (searchProductNameString.equals("")) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Search Warning");
        alert.setHeaderText("Your search did not match any results.");
        alert.setContentText("You did not enter a product to search for.");
        alert.showAndWait();
    }
    else {
        boolean found = false;
        try {
            Product searchProduct = Inventory.lookupProduct(searchProductNameString);

            if (searchProduct == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Search Warning");
                alert.setHeaderText("There were no products found.");
                alert.setContentText("The item you searched for does not match any results.");
                alert.showAndWait();
            } else {
                for (Product p : getProducts()) {
                    if (p.getName().contains(searchProductNameString)) {
                        found = true;
                        filteredProductsList.add(p);
                        viewProducts.setItems(filteredProductsList);
                    }
                }
                if (found == false) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Product Search Warning");
                    alert.setHeaderText("There were no products found!");
                    alert.setContentText("The search term entered does not match any product names!");
                    alert.showAndWait();
                }

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Set up columns of Parts table
        partIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInStockCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        //Set up columns for Products table
        productIDCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        //Load Initial dummy data
        viewParts.setItems(Inventory.getParts());
        viewProducts.setItems(Inventory.getProducts());
    }


}
