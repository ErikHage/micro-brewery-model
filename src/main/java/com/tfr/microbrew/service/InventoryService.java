package com.tfr.microbrew.service;

import com.tfr.microbrew.exception.InventoryException;
import com.tfr.microbrew.model.InventoryItem;

/**
 * Service layer for Inventory interaction
 *
 * Created by Erik on 4/22/2017.
 */
public interface InventoryService {

    void addItem(InventoryItem inventoryItem) throws InventoryException;

    InventoryItem getItemByName(String name);
    double getCurrentQuantity(String name);

    void updateItem(InventoryItem inventoryItem);
    void updateQuantity(String name, double changeInQuantity) throws InventoryException;

    void deleteItem(String name);

}
