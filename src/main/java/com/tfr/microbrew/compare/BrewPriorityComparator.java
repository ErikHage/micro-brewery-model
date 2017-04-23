package com.tfr.microbrew.compare;

import com.tfr.microbrew.model.Batch;
import com.tfr.microbrew.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * Compares batch priority based on the remaining inventory. A lower quantity in inventory results
 * in a higher priority.
 *
 * Created by Erik on 4/22/2017.
 */
@Component("BatchPriorityComparator")
public class BrewPriorityComparator implements Comparator<Batch> {

    private final InventoryService inventoryService;

    @Autowired
    public BrewPriorityComparator(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public int compare(Batch b1, Batch b2) {
        double b1Quantity = inventoryService.getCurrentQuantity(b1.getRecipe().getName());
        double b2Quantity = inventoryService.getCurrentQuantity(b2.getRecipe().getName());

        if(b1Quantity > b2Quantity) {
            return 1;
        } else if(b1Quantity < b2Quantity) {
            return -1;
        }
        return 0;
    }
}

