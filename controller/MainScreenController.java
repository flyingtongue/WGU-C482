package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.*;

/**
 *  MainScreenController - provides logic for the main screen. The main UI and
 *  features to allow for interaction with the inventory.
 */

public class MainScreenController implements Initializable {

    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> productIDCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productInventoryCol;
    @FXML private TableColumn<Product, Double> productPriceCol;
    @FXML private TextField productSearch;
    @FXML private TextField partSearch;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInventoryCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private TableView<Product> mainScreenProductsTable;
    @FXML private TableView<Part> mainScreenPartsTable;
    @FXML private TableView<Part> modifyProductTable;
    @FXML private TableColumn<Part, Integer> partsTablePartID;
    @FXML private TableColumn<Part, String> partsTablePartName;
    @FXML private TableColumn<Part, Integer> partsTableInventoryCount;
    @FXML private TableColumn<Part, Integer> partsTablePPU;
    @FXML private TableColumn<Product, Integer> productsTablePartID;
    @FXML private TableColumn<Product, String> productsTablePartName;
    @FXML private TableColumn<Product, Integer> productsTableInventoryCount;
    @FXML private TableColumn<Product, Integer> productsTablePPU;
    @FXML private Button modifyPartButton;
    @FXML private Button addPartButton;
    @FXML private Button deletePartButton;
    @FXML private Button exitMain;
    @FXML private TableView<Part> partsTableView;

    Stage stage;

    /**
     * Loads the AddPart.fxml - user interface for adding a part to inventory.
     * @param event
     */
    @FXML
    void MainScreenAddPartsClick(ActionEvent event) throws IOException {

        Parent addParts = FXMLLoader.load(getClass().getResource("../views/AddPart.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Loads the ModifyPart controller.
     * @param event
     */
    @FXML
    void MainScreenModifyPartsClick(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(mainScreenPartsTable.getSelectionModel().getSelectedIndex(),mainScreenPartsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a part first");
            alert.show();
        }
    }

    /**
     * Loads the AddProduct controller.
     * @param event
     */
    @FXML
    void MainScreenAddProductsClick(ActionEvent event) throws IOException {

        Parent addParts = FXMLLoader.load(getClass().getResource("../views/AddProduct.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Exits the application.
     * @param ExitButton
     */
    public void mainScreenExitButton(ActionEvent ExitButton) {
        Stage stage = (Stage) ((Node) ExitButton.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Loads the ModifyProduct controller.
     * @param event
     */
    @FXML
    void MainScreenModifyProductsClick(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(mainScreenProductsTable.getSelectionModel().getSelectedIndex(), mainScreenProductsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a product to modify");
            alert.show();
        }
    }

    /**
     * Initialize and populate the tables.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainScreenPartsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainScreenProductsTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Deletes the selected part.
     * @param event
     */
    @FXML
    void mainScreenDeletePartButton(ActionEvent event) {
        Part selectedPart = mainScreenPartsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }

    /**
     * Deletes the selected product while checking it for associated parts.
     * @param event
     */

    @FXML
    void mainScreenDeleteProductButton(ActionEvent event) {
        Product selectedProduct = mainScreenProductsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Product selectedDeleteProduct = mainScreenProductsTable.getSelectionModel().getSelectedItem();
            if (selectedDeleteProduct.getAllAssociatedParts().size() > 0) {
                Alert cantDelete = new Alert(Alert.AlertType.ERROR);
                cantDelete.setTitle("Error Message");
                cantDelete.setContentText("Remove associated parts before you delete the product.");
                cantDelete.showAndWait();
                return;
            }
            Inventory.deleteProduct(selectedProduct);
        }
    }

    /**
     * Search for text with in the parts search field by pressing enter.
     * FUTURE ENHANCEMENT: All search functions should return a part whether or not
     * the part is uppercase or lowercase. Right now the search functions are
     * only returning the same case as they are displayed in the parts or products
     * area of the program.
     * @param event
     */
    @FXML
    void mainScreenPartSearch(ActionEvent event) {
        String searchText = partSearch.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            mainScreenPartsTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Part not found");
            noParts.showAndWait();
        }
    }

    /**
     * Search for text with in the products search field by pressing enter.
     * @param event
     */
    @FXML
    void mainScreenProductSearch(ActionEvent event) {
        String searchText = productSearch.getText();
        ObservableList<Product> results = Inventory.lookupProduct(searchText);
        try {
            while (results.size() == 0 ) {
                int productID = Integer.parseInt(searchText);
                results.add(Inventory.lookupProduct(productID));
            }
            mainScreenProductsTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Product not found");
            noParts.showAndWait();
        }
    }
}
