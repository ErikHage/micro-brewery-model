package com.tfr.microbrew.controller;

import com.tfr.microbrew.config.Constants;
import com.tfr.microbrew.model.InitialParameters;
import com.tfr.microbrew.model.InventoryItem;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Set;

/**
 * Tests for ModelController
 *
 * Created by Erik on 4/22/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestModelController {

    @Autowired
    private ModelController modelController;

    @Autowired
    private Set<InventoryItem> inventoryItems;

    @Test
    public void test() {
        InitialParameters initialParameters = new InitialParameters();
        initialParameters.setStartDate(new LocalDate(2017, 4, 1));
        initialParameters.setEndDate(new LocalDate(2017, 5, 1));
        initialParameters.setInitialInventory(new HashMap<>());
        initialParameters.setContextId("test" + System.currentTimeMillis());

        initialParameters.getInitialInventory()
                .put("Checks and Balances IPA", Constants.BrewHouse.BATCH_SIZE * 2);

        inventoryItems.stream()
                .filter(i -> i.getCategory().equals(Constants.InventoryCategory.BEER))
                .forEach(i -> initialParameters.getInitialInventory()
                        .putIfAbsent(i.getName(), Constants.BrewHouse.BATCH_SIZE)
                );

        inventoryItems.stream()
                .filter(i -> ! i.getCategory().equals(Constants.InventoryCategory.BEER))
                .forEach(i -> initialParameters.getInitialInventory()
                        .put(i.getName(), i.getReorderQuantity())
                );

        modelController.runModel(initialParameters);
    }
}
