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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import InventorySystem.Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static InventorySystem.Controller.MainScreenController.getSelectedProduct;
import static InventorySystem.Controller.MainScreenController.getSelectedProductIndex;
import static InventorySystem.Model.Inventory.getParts;

/*
 *
 * Aaron Artz
 * May 1, 2020
 * WGU C482 Final
 *
 */


public class ModifyProductController implements Initializable {

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

    @FXML public static ObservableList<Part> productAssociatedParts = FXCollections.observableArrayList();

    @FXML public ObservableList<Part> currentParts = FXCollections.observableArrayList();

    @FXML private String invalidFieldWarning = new String();


    @FXML private int productID;

    Product newProduct;

    Product selectedProduct;

    int selectedIndex;



    @FXML
    void addButtonAction(ActionEvent event) throws IOException {
        Part singlePart = viewAllParts.getSelectionModel().getSelectedItem();
        selectedProduct.addAssociatedPart(singlePart);
        updateViewProductParts();
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

    @FXML
    public void deleteButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
    public void searchButtonAction(ActionEvent event) {
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
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

            Inventory.updateProduct(getSelectedProductIndex(), newProduct);

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


@FXML private String errProductValid = new String();

@FXML private String errProductField = new String();



// Sets up text fields and assigns Product.

    public void setProduct(Product product, int index) {
        selectedProduct = product;
        selectedIndex = index;
        if (product instanceof Product) {
            //Product newProduct = (Product) product;
            newProduct = (Product) product;
         

            this.txtFieldName.setText(selectedProduct.getName());
            this.txtFieldInv.setText(Integer.toString(selectedProduct.getStock()));
            this.txtFieldPrice.setText(Double.toString(selectedProduct.getPrice()));
            this.txtFieldMin.setText(Integer.toString(selectedProduct.getMin()));
            this.txtFieldMax.setText(Integer.toString(selectedProduct.getMax()));
            this.viewProductParts.setItems(selectedProduct.getAllAssociatedParts());
            this.txtFieldID.setText(Integer.toString(selectedProduct.getProductID()));
        }
    }

    public void updateAllPartView() {
        viewAllParts.setItems(Inventory.getParts());
    }

    public void updateViewProductParts () {viewProductParts.setItems(newProduct.getAllAssociatedParts());}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        newProduct = new Product(0, null, 0.0, 0, 0, 0);


        //Set Columns of viewAllParts
        allPartsIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("price"));

        //set Columns of ViewProductParts
        productPartsIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        productPartsNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productPartsInvCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        productPartsPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("price"));

        updateAllPartView();
        updateViewProductParts();

        txtFieldID.setEditable(false);
        txtFieldID.setMouseTransparent(true);
        txtFieldID.setFocusTraversable(false);
    }
}
