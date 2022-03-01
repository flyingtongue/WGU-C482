package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * The inventory class stores the parts and products in observable lists.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the allParts observable list.
     * @param newPart
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * Adds a new product to the allProducts observable list.
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * For loop to iterate all parts and return part if found.
     * @param partID
     * @return part if found and null if no item has been found
     */
    public static Part lookupPart(int partID) {
        for(Part part: Inventory.getAllParts()) {
            while (part.getId() == partID) {
                return part;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No item found");
        alert.show();
        return null;
    }

    /**
     * Iterate through a
     * @param productID
     * @return product if found and null if no item has been found.
     */
    public static Product lookupProduct(int productID) {
        for(Product product: Inventory.getAllProducts()){
            while (product.getId() == productID)
                return product;
        }
        return null;
    }

    /**
     * lookupPart creates an observablelist of filtered parts and returns the part if found.
     * @param partName
     * @return filtered parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> PartName = FXCollections.observableArrayList();

        for (Part part: allParts) {
            if (part.getName().contains(partName)) {
                PartName.add(part);
            }
        }
        return PartName;
    }

    /**
     * lookupProduct creates an observablelist of filtered product and returns the part if found.
     * @param productName
     * @return filteredProduct
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> ProductName = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            if (product.getName().contains(productName)) {
                ProductName.add(product);
            }
        }
        return ProductName;
    }

    /**
     * Update the part with an observable list.
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * Update the product within an observable list.
     * @param index
     * @param selectedProduct
     */

    public static void updateProduct(int index, Product selectedProduct) {

        allProducts.set(index, selectedProduct);
    }


    /**
     * Delete a part within an observable list.
     * @param selectedPart
     */

    public static boolean deletePart (Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Delete a product within an observable list.
     * @param selectedProduct
     */
    public static boolean deleteProduct (Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns all products in the all products observable list.
     * @return allProducts observablelist
     */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

    /**
     * Returns all parts in the all products observable list.
     * @return allParts observablelist
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }
}