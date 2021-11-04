package InventorySystem.Controller;

import InventorySystem.Model.InHouse;
import InventorySystem.Model.Inventory;
import InventorySystem.Model.OutSourced;
import InventorySystem.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/*
 *
 * Aaron Artz
 * May 1, 2020
 * WGU C482 Final
 *
 */


public class AddPartController implements Initializable {

    @FXML private RadioButton rbAddPartInHouse;

    @FXML private ToggleGroup tglSource;

    @FXML private RadioButton rbAddPartOutsourced;

    @FXML private Label labelAddPart;

    @FXML private TextField txtAddPartID;

    @FXML private TextField txtAddPartName;

    @FXML private TextField txtAddPartInv;

    @FXML private TextField txtAddPartPrice;

    @FXML private TextField txtAddPartMax;

    @FXML private TextField txtAddPartDyn;

    @FXML private TextField txtAddPartMin;

    @FXML private Button saveButton;

    @FXML private String invalidPartText = new String();

    @FXML private String errPartField = new String();

    @FXML private boolean isPartOutsourced;

    @FXML private int partID;

    private boolean isOutsourced = true;

    @FXML
    void idAddPartText(ActionEvent event) {
    }

    @FXML
    void rbInHouseAction(ActionEvent event) {
        isOutsourced = false;
        txtAddPartDyn.setText("Machine ID");
        labelAddPart.setText("Machine ID");
    }

    @FXML
    void inventoryAddPartText(ActionEvent event) {
    }

    @FXML
    void dynamicAddPartText(ActionEvent event) {
    }

    @FXML
    void maxAddPartText(ActionEvent event) {
    }

    @FXML
    void minAddPartText(ActionEvent event) {
    }


    @FXML
    void nameAddPartText(ActionEvent event) {
    }

    @FXML
    void rbOutsourcedAction(ActionEvent event) {
        isOutsourced = true;
        txtAddPartDyn.setText("Company Name");
        labelAddPart.setText("Company Name");
    }

    @FXML
    void priceAddPartText(ActionEvent event) {
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
    void saveButtonAction(ActionEvent event) throws IOException {

        int ID = 0;
        for(Part part : Inventory.getParts()) {

            if(part.getPartID() > ID)

                ID = part.getPartID();
        }

        txtAddPartID.setText(String.valueOf(++ID));
        String name = txtAddPartName.getText();
        int inventory = Integer.parseInt(txtAddPartInv.getText());
        double priceCost = Double.parseDouble(txtAddPartPrice.getText());
        int max = Integer.parseInt(txtAddPartMax.getText());
        int min = Integer.parseInt(txtAddPartMin.getText());

        invalidPartText = Part.invalidPartWarning(inventory, priceCost, max, min, invalidPartText);
        try{

            if(invalidPartText.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Product Addition Warning");
                alert.setHeaderText("The product you entered was NOT added!");
                alert.setContentText(invalidPartText);
                alert.showAndWait();
                invalidPartText = "";
            }
            else {
                if (rbAddPartInHouse.isSelected()) {
                    int machineID = Integer.parseInt(txtAddPartDyn.getText());
                    InHouse addPart = new InHouse(ID, name, priceCost, inventory, min, max, machineID);

                    Inventory.addPart(addPart);
                }
                if (rbAddPartOutsourced.isSelected()) {
                    String companyName = txtAddPartDyn.getText();
                    OutSourced addPart = new OutSourced(ID, name, priceCost, inventory, min, max, companyName);

                    Inventory.addPart(addPart);
                }
                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtAddPartID.setEditable(false);
        txtAddPartID.setMouseTransparent(true);
        txtAddPartID.setFocusTraversable(false);
    }
}
