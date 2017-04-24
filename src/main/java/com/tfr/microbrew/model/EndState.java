package com.tfr.microbrew.model;

import java.util.Set;

/**
 *
 *
 * Created by Erik on 4/23/2017.
 */
public class EndState {

    private Set<InventoryItem> inventory;




    public Set<InventoryItem> getInventory() {
        return inventory;
    }

    public void setInventory(Set<InventoryItem> inventory) {
        this.inventory = inventory;
    }
}
