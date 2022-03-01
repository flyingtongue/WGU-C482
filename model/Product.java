package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The product class is structured after the UML diagram provided in the course documentation.
 * The product class provides product objects and associated parts.
 */

public class Product {
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id
     */
    public void setId(int id) { this.id = id; }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds a part to the associated parts observablelist.
     * @param part
     */
    public void addAssociatedParts(Part part) {
        associatedParts.add(part);
    }

    /**
     * Remove a part from the observablelist.
     * @param selectedAssociatedPart
     * @return true
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * Returns all associated parts for selected product.
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Remove a part from the observablelist.
     * @param selectedAssociatedPart
     * @return true
     */
    public static boolean deleteAssocdPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
}




