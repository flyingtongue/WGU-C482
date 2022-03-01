package controller;

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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Product;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 *  AddProductController - manipulate the product inventory.
 */

public class AddProductController implements Initializable {

    // This observablelist holds the associatedPartsList.
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML private TextField addProductSearchBox;
    @FXML private Button addProductSearchButton;
    @FXML private TextField addProductID;
    @FXML private TextField addProductName;
    @FXML private TextField addProductInv;
    @FXML private TextField addProductPrice;
    @FXML private TextField addProductMax;
    @FXML private TextField addProductMin;
    @FXML private TableView<Part> associatedProductTable;
    @FXML private TableView<Part> addProductTable;
    @FXML private TableColumn<?, ?> addProductPartIDCol;
    @FXML private TableColumn<?, ?> addPartNameCol;
    @FXML private TableColumn<?, ?> addProductInventoryCol;
    @FXML private TableColumn<?, ?> addProductPriceCol;
    @FXML private TableColumn<?, ?> associatedProductIDCol;
    @FXML private TableColumn<?, ?> associatedPartNameCol;
    @FXML private TableColumn<?, ?> associatedInventoryCol;
    @FXML private TableColumn<?, ?> associatedPriceCol;
    @FXML private Button addProductCancelButton;
    @FXML private Button addProductSaveButton;
    @FXML private Button removeAssociatedPartButton;
    @FXML private Button addProductAddButton;
    @FXML private Button removeAssocPartButton;

    /**
     * On button press, go to the main screen.
     * @param event
     */

    @FXML
    public void addProductCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();
    }

    /**
     * Save new product.
     * @param event
     */
    @FXML
    void productSavePushed(ActionEvent event) throws IOException {
        try {

            // Create random number and multiply by 10000, this will keep it from overlapping with parts.
            int uniqueID = (int) (Math.random() * 10000);

            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductInv.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());

            if (min > stock || stock < max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory requirements: Inventory must be within min and max.");
                alert.showAndWait();
            } else if (min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory requirements: maximum must be greater than minimum");
                alert.showAndWait();
            }

            Product product = new Product(uniqueID, name, price, stock, min, max);

            for (Part part: associatedPartsList) {
                if (part != associatedPartsList)
                    product.addAssociatedParts(part);
            }

            Inventory.getAllProducts().add(product);

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

    /**
     * Add the part to the observablelist.
     * @param event
     */

    @FXML
    void addProductAdd(ActionEvent event) {
        Part selectedPart = addProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
            return;
        }
        else if (!associatedPartsList.contains(selectedPart))
        {
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * Remove the part to the observablelist.
     * @param event
     */
    @FXML
    void removeAssocPartButton(ActionEvent event) {
        Part selectedPart = associatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
            return;
        }
        else if (associatedPartsList.contains(selectedPart))
        {
            associatedPartsList.remove(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     *  Initialize and populate the table with products and associated parts.
     * @param resourceBundle
     * @param url
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addProductTable.setItems(Inventory.getAllParts());
        addProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //add parts to associated table (bottom)
        associatedProductTable.setItems(associatedPartsList);
        associatedProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     *  Implements search box to filter parts based off of text.
     * @param event
     */
    @FXML
    void addProductPartSearch(ActionEvent event) {
        String searchText = addProductSearchBox.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            addProductTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Part not found");
            noParts.showAndWait();
        }
    }

}
