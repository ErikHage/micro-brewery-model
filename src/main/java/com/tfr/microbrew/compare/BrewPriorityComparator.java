package com.tfr.microbrew.compare;

import com.tfr.microbrew.model.Batch;
import com.tfr.microbrew.model.InventoryItem;
import com.tfr.microbrew.service.BatchService;
import com.tfr.microbrew.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;

import static com.tfr.microbrew.config.Constants.BrewHouse.BATCH_SIZE;

/**
 * Compares batch priority based on the remaining inventory. A lower quantity in inventory results
 * in a higher priority.
 *
 * Created by Erik on 4/22/2017.
 */
@Component("BatchPriorityComparator")
public class BrewPriorityComparator implements Comparator<Batch> {

    private final InventoryService inventoryService;
    private final BatchService batchService;

    @Autowired
    public BrewPriorityComparator(BatchService batchService,
                                  InventoryService inventoryService) {
        this.batchService = batchService;
        this.inventoryService = inventoryService;
    }

    @Override
    public int compare(Batch b1, Batch b2) {
        //higher priority for products with a larger difference between the current quantity and the reorder threshold
        double b1Diff = getAdjustedDifference(b1.getRecipe().getName());
        double b2Diff = getAdjustedDifference(b2.getRecipe().getName());

        if(b1Diff > b2Diff) {
            return 1;
        } else if(b1Diff < b2Diff) {
            return -1;
        }
        return 0;
    }

    /**
     * Difference between current adjusted quantity (in inventory + in progress batches) and reorder threshold
     *
     * @param productName
     * @return
     */
    private double getAdjustedDifference(String productName) {
        InventoryItem item = inventoryService.getItemByName(productName);
        double inProgressQuantity = batchService.getByRecipe(productName).size() * BATCH_SIZE;
        double adjustedQuantity = inProgressQuantity + item.getQuantity();

        return item.getReorderThreshold() - adjustedQuantity;
    }
}

