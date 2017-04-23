package com.tfr.microbrew.dao;

import com.tfr.microbrew.model.InventoryItem;

import java.util.Set;

/**
 * Interface for Inventory data access
 *
 * Created by Erik on 4/22/2017.
 */
public interface InventoryDao {

    void create(InventoryItem inventoryItem);
    Set<InventoryItem> readAll();
    InventoryItem readByName(String name);
    void update(InventoryItem inventoryItem);
    void delete(InventoryItem inventoryItem);

}
