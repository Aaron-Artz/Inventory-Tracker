package InventorySystem.Controller;

import InventorySystem.Model.Product;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import InventorySystem.Model.Part;
import InventorySystem.Model.Inventory;
import  InventorySystem.Model.Product;

import static InventorySystem.Model.Inventory.getParts;

/*
 *
 * Aaron Artz
 * May 1, 2020
 * WGU C482 Final
 *
 */


public class AddProductController implements Initializable{

    @FXML private Button cancelButton;

    @FXML private Button saveButton;

    @FXML private Button addButton;

    @FXML private Button deleteButton;

    @FXML private Button searchButton;

    @FXML private TextField txtFieldSearch;

    @FXML private TextField txtFieldID;

    @FXML private TextField txtFieldName;

    @FXML private TextField txtFieldInv;

    @FXML private TextField txtFieldPrice;

    @FXML private TextField txtFieldMax;

    @FXML private TextField txtFieldMin;

    @FXML private TableView<Part> viewAllParts;

    @FXML private TableView<Part> viewProductParts;

    @FXML private TableColumn<Part, Integer> allPartsIDCol;

    @FXML private TableColumn<Part, String> allPartsNameCol;

    @FXML private TableColumn<Part, Integer> allPartsInvCol;

    @FXML private TableColumn<Part, Integer> allPartsPriceCol;

    @FXML private TableColumn<Part, Integer> productPartsIDCol;

    @FXML private TableColumn<Part, String> productPartsNameCol;

    @FXML private TableColumn<Part, Integer> productPartsInvCol;

    @FXML private TableColumn<Part, Integer> productPartsPriceCol;

    Product newProduct;

    Part selectedPart;

    @FXML private String errProductValid = new String();

    @FXML private String errProductField = new String();

    @FXML private int productID;

    @FXML private String emptyFieldWarning = new String();

    @FXML private String invalidFieldWarning = new String();


    @FXML
    public void searchPartButtonAction(ActionEvent event) {
        String searchPartNameString = txtFieldSearch.getText();
        ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
        if (searchPartNameString.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
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
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Search Warning");
                    alert.setHeaderText("There were no parts found.");
                    alert.setContentText("The item you searched for does not match any results.");
                    alert.showAndWait();
                } else {
                    for (Part p : getParts()) {
                        if (p.getName().contains(searchPartNameString)) {
                            found = true;
                            filteredPartsList.add(p);
                            viewAllParts.setItems(filteredPartsList);
                        }
                    }
                    if (found == false) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
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
    public void updateAllPartView() {
        viewAllParts.setItems(Inventory.getParts());
    }



    @FXML
    void addButtonAction(ActionEvent event) {
        Part part = viewAllParts.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedPart(part);
        updateProductPartsView();
    }


    public void updateProductPartsView() {
        viewProductParts.setItems(newProduct.getAllAssociatedParts());
    }
// ******************************************************Add delete Alert? \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/
    @FXML
    public void deleteButtonAction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Part Confirmation");
        alert.setHeaderText("You are about to remove a Part!");
        alert.setContentText("Press OK to Remove the Part. \nPress Cancel to return to the add product screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Part part = viewProductParts.getSelectionModel().getSelectedItem();
            newProduct.deleteAssociatedPart(part.getPartID());
        }
        else {
            alert.close();
        }
    }

    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        int ID = 0;
        for(Product product : Inventory.getAllProducts()) {

            if(product.getProductID() > ID)
                ID = product.getProductID();

        }

        String name = txtFieldName.getText();
        int inventory = Integer.parseInt(txtFieldInv.getText());
        double price = Double.parseDouble(txtFieldPrice.getText());
        int max = Integer.parseInt(txtFieldMax.getText());
        int min = Integer.parseInt(txtFieldMin.getText());
        txtFieldID.setText(String.valueOf(++ID));
        ObservableList<Part> associatedParts = viewProductParts.getItems();

        try{
            invalidFieldWarning = Product.getProductValidation(inventory, price, max, min, invalidFieldWarning);

            if (invalidFieldWarning.length() > 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Product Addition Warning");
                alert.setHeaderText("The product you entered was NOT added!");
                alert.setContentText(invalidFieldWarning);
                alert.showAndWait();
                invalidFieldWarning = "";
            }
            else if (associatedParts.isEmpty()){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Product Addition Warning");
                alert.setHeaderText("The product you entered was NOT added!");
                alert.setContentText("The product must have at least one part.");
                alert.showAndWait();
            }

            else {
                newProduct.setProductID(ID);
                newProduct.setName(name);
                newProduct.setPrice(price);
                newProduct.setMax(max);
                newProduct.setMin(min);
                newProduct.setStock(inventory);
                // Previouse setter
                // newProduct.setAllAssociatedParts(viewProductParts.getItems());

                Inventory.addProduct(newProduct);


                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        }
        catch(NumberFormatException e){


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();

        }
    }



    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Canceling will erase all unsaved text fields and return to the Main Screen.\nWould you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newProduct = new Product(0, null, 0, 0, 0, 0);
        updateAllPartView();



        //IM ONTO SOMETHING HERE I THINK!!!!!!!
        updateProductPartsView();
        // viewProductParts.setItems(newProduct.getAllAssociatedParts());

        //Set Columns of viewAllParts
        allPartsIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("price"));

        //set Columns of ViewProductParts
        productPartsIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        productPartsNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productPartsInvCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
        productPartsPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("price"));

        txtFieldID.setEditable(false);
        txtFieldID.setMouseTransparent(true);
        txtFieldID.setFocusTraversable(false);
    }
}
