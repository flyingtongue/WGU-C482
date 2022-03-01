package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;

/**
 * Controller to define the ModifyPartController class.
 */

public class ModifyPartController  {

    // label for swapping between machine id and outsourced
    @FXML private RadioButton ModifyPartInHouseRadio;
    @FXML private RadioButton ModifyPartOutsourcedRadio;
    @FXML private Label MachineIDorCompany;
    @FXML private TextField modifyPartID;
    @FXML private TextField modifyPartName;
    @FXML private TextField modifyPartInv;
    @FXML private TextField modifyPartPrice;
    @FXML private TextField modifyPartMax;
    @FXML private TextField modifyPartMin;
    @FXML private TextField addPartMachineID;
    @FXML private Button modifyPartCancelButton;

    private int currentIndex = 0;

    /**
     *  Cancel button - return to main screen.
     * @param event
     */

    @FXML
    public void ModifyPartCancelAction (ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();
    }

    /**
     * Receive information from the main screen.
     * @param part
     * @param selectedIndex
     */
    public void sendPart(int selectedIndex, Part part){

        currentIndex = selectedIndex;

        if (part instanceof InHouse) {
            ModifyPartInHouseRadio.setSelected(true);
            addPartMachineID.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
        else {
            ModifyPartOutsourcedRadio.setSelected(true);
            addPartMachineID.setText(((Outsourced) part).getCompanyName());
        }

        modifyPartID.setText(String.valueOf(part.getId()));
        modifyPartName.setText(String.valueOf(part.getName()));
        modifyPartInv.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMax.setText(String.valueOf(part.getMax()));
        modifyPartMin.setText(String.valueOf(part.getMin()));
    }

    /**
     * Change part type label to in house.
     * @param event
     */

    @FXML
    public void onActionaddPartInHouse(ActionEvent event) {

        MachineIDorCompany.setText("Machine ID");
    }
    /**
     *  Change part type label to outsourced.
     * @param event
     */
    @FXML
    public void onActionaddPartOutsourced(ActionEvent event) {

        MachineIDorCompany.setText("Company Name");
    }


    /**
     * Button action to save the modified part.
     * @param event
     */
    @FXML
    void modifyPartSaveButton(ActionEvent event) throws IOException {
        try {
            int partID = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            int inStock = Integer.parseInt(modifyPartInv.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            // broke with parseint
            int machineID;

            String companyName;

            //Min should be less than max.
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
                alert.showAndWait();
                return;
            }
            //Inventory should be between the min and max values.
            else if (inStock < min || max < inStock) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();
                return;
            }

            // Print to console for troubleshooting.
            System.out.println(ModifyPartOutsourcedRadio.isSelected() + " outsourced");
            System.out.println(ModifyPartInHouseRadio.isSelected() + " inhouse");
            if (ModifyPartInHouseRadio.isSelected()) {
                machineID = Integer.parseInt(addPartMachineID.getText());
                InHouse updatedPart = new InHouse(partID, name, price, inStock, min, max, machineID);
                Inventory.updatePart(currentIndex, updatedPart);
            }
            if (ModifyPartOutsourcedRadio.isSelected()) {
                companyName = addPartMachineID.getText();
                Outsourced updatedPart = new Outsourced(partID, name, price, inStock, min, max, companyName);
                Inventory.updatePart(currentIndex, updatedPart);
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Incorrect value");
            alert.showAndWait();
            return;
        }
    }

}

