package InventorySystem.Controller;

import InventorySystem.Model.*;
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



public class ModifyPartController implements Initializable {

    Part selectedPart;
    int selectedIndex;

    @FXML private RadioButton rbModifyPartInHouse;

    @FXML private RadioButton rbModifyPartOutsourced;

    @FXML private Label labelModifyPart;

    @FXML private TextField txtModifyPartName;

    @FXML private TextField txtModifyPartInv;

    @FXML private TextField txtModifyPartPrice;

    @FXML private TextField txtModifyPartMax;

    @FXML private TextField txtModifyPartDyn;

    @FXML private TextField txtModifyPartMin;

    @FXML private TextField txtModifyPartID;

    @FXML private String invalidPartText = new String();

    @FXML void rbOutsourcedAction(ActionEvent event) {
       labelModifyPart.setText("Company Name");
    }

    @FXML void rbInHouseAction(ActionEvent event) {
        labelModifyPart.setText("Machine ID");
    }

    @FXML
    public void cancelButtonAction (ActionEvent event) throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Canceling will erase all unsaved text fields and return to the Main Screen. \nWould you like to continue?");
        Optional<ButtonType> cancel = alert.showAndWait();

        if(cancel.isPresent() && cancel.get() == ButtonType.OK) {
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    public void setPart(Part part, int index) {
        selectedPart = part;
        selectedIndex = index;

        if (part instanceof InHouse) {
            InHouse newPart = (InHouse) part;
            rbModifyPartInHouse.setSelected(true);
            labelModifyPart.setText("Machine ID");
            this.txtModifyPartName.setText(newPart.getName());
            this.txtModifyPartPrice.setText(Double.toString(newPart.getPrice()));
            this.txtModifyPartInv.setText((Integer.toString(newPart.getInStock())));
            this.txtModifyPartMax.setText(Integer.toString(newPart.getMax()));
            this.txtModifyPartMin.setText(Integer.toString(newPart.getMin()));
            this.txtModifyPartDyn.setText(Integer.toString((newPart.getMachineID())));
            this.txtModifyPartID.setText(Integer.toString(newPart.getPartID()));
        }

        if (part instanceof OutSourced) {
            OutSourced newPart = (OutSourced) part;
            rbModifyPartOutsourced.setSelected(true);
            labelModifyPart.setText("Company Name");
            this.txtModifyPartName.setText(newPart.getName());
            this.txtModifyPartPrice.setText(Double.toString(newPart.getPrice()));
            this.txtModifyPartInv.setText(Integer.toString(newPart.getInStock()));
            this.txtModifyPartMax.setText(Integer.toString(newPart.getMax()));
            this.txtModifyPartMin.setText(Integer.toString(newPart.getMin()));
            this.txtModifyPartDyn.setText(newPart.getCompanyName());
            this.txtModifyPartID.setText(Integer.toString(newPart.getPartID()));
        }
    }

    @FXML
    void saveButtonAction (ActionEvent event) throws IOException {
        int partID = selectedPart.getPartID();
        String name = txtModifyPartName.getText();
        double price = Double.parseDouble(txtModifyPartPrice.getText());
        int inv = Integer.parseInt(txtModifyPartInv.getText());
        int min = Integer.parseInt(txtModifyPartMin.getText());
        int max = Integer.parseInt(txtModifyPartMax.getText());
        invalidPartText = Part.invalidPartWarning(inv, price, max, min, invalidPartText);

        if  (invalidPartText.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Product Addition Warning");
            alert.setHeaderText("The product you entered was NOT added!");
            alert.setContentText(invalidPartText);
            alert.showAndWait();
            invalidPartText = "";
        }

        else {
            if (rbModifyPartInHouse.isSelected()) {
                int machID = Integer.parseInt(txtModifyPartDyn.getText());
                Part inHousePart = new InHouse(partID, name, price, inv, min, max, machID);
                Inventory.getParts().set(selectedIndex, inHousePart);
            }
            if (rbModifyPartOutsourced.isSelected()) {
                String companyName = txtModifyPartDyn.getText();
                Part outsourcedPart = new OutSourced(partID, name, price, inv, min, max, companyName);
                Inventory.getParts().set(selectedIndex, outsourcedPart);
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtModifyPartID.setEditable(false);
        txtModifyPartID.setMouseTransparent(true);
        txtModifyPartID.setFocusTraversable(false);
    }
}
