package com.tfr.microbrew.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * Created by Erik on 4/22/2017.
 */
public class InventoryItem {

    private String name;
    private String category;
    private double quantity;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private double reorderThreshold;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private double reorderQuantity;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private double unitPrice;

    public InventoryItem() {
        //required for jackson
    }

    public InventoryItem(String name, String category, double quantity, double reorderThreshold, double reorderQuantity, double unitPrice) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.reorderThreshold = reorderThreshold;
        this.reorderQuantity = reorderQuantity;
        this.unitPrice = unitPrice;
    }

    public InventoryItem(String name, String category, double reorderThreshold, double reorderQuantity, double unitPrice) {
        this(name, category, 0.0, reorderThreshold, reorderQuantity, unitPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryItem that = (InventoryItem) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", reorderThreshold=" + reorderThreshold +
                ", reorderQuantity=" + reorderQuantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getReorderThreshold() {
        return reorderThreshold;
    }

    public void setReorderThreshold(double reorderThreshold) {
        this.reorderThreshold = reorderThreshold;
    }

    public double getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(double reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
