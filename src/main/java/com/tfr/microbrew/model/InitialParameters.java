package com.tfr.microbrew.model;

import org.joda.time.LocalDate;

import java.util.Set;

/**
 * Starting parameter for model
 *
 * Created by Erik on 4/22/2017.
 */
public class InitialParameters {

    private Set<InventoryItem> initialInventory;

    private LocalDate startDate;
    private LocalDate endDate;

    public InitialParameters() {

    }

    public Set<InventoryItem> getInitialInventory() {
        return initialInventory;
    }

    public void setInitialInventory(Set<InventoryItem> initialInventory) {
        this.initialInventory = initialInventory;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
