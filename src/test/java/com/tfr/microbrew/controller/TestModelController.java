package com.tfr.microbrew.controller;

import com.tfr.microbrew.MicroBreweryModelApplication;
import com.tfr.microbrew.config.Constants;
import com.tfr.microbrew.model.InitialParameters;
import com.tfr.microbrew.model.InventoryItem;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Set;

@SpringBootTest(classes = MicroBreweryModelApplication.class)
public class TestModelController {

    @Autowired
    private ModelController modelController;

    @Autowired
    @Qualifier("InventoryItems")
    private Set<InventoryItem> inventoryItems;

    /**
     * Run Simulation with full starting inventory and double batches in stock
     */
    @Test
    public void test_FullInventory_DoubleBeerBatches() {
        InitialParameters initialParameters = new InitialParameters();
        initialParameters.setStartDate(new LocalDate(2017, 4, 1));
        initialParameters.setEndDate(new LocalDate(2017, 10, 1));
        initialParameters.setInitialInventory(new HashMap<>());
        initialParameters.setContextId("test_FullInventory_DoubleBatches_" + System.currentTimeMillis());

        inventoryItems.stream()
                .filter(i -> i.getCategory().equals(Constants.InventoryCategory.BEER))
                .forEach(i -> initialParameters.getInitialInventory()
                        .putIfAbsent(i.getName(), Constants.BrewHouse.BATCH_SIZE * 2)
                );

        inventoryItems.stream()
                .filter(i -> ! i.getCategory().equals(Constants.InventoryCategory.BEER))
                .forEach(i -> initialParameters.getInitialInventory()
                        .put(i.getName(), i.getReorderQuantity())
                );

        modelController.runModel(initialParameters);
    }
}
