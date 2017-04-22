package com.tfr.microbrew.controller;

import com.google.common.collect.Sets;
import com.tfr.microbrew.model.InitialParameters;
import com.tfr.microbrew.model.InventoryItem;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void test() {
        InitialParameters initialParameters = new InitialParameters();
        initialParameters.setStartDate(new LocalDate(2017, 4, 1));
        initialParameters.setEndDate(new LocalDate(2017, 5, 1));

        Set<InventoryItem> initialInventory = Sets.newHashSet(
                new InventoryItem("Malted Barley - 2 Row", 200.0, 100.0, 500.0),
                new InventoryItem("Malted Barley - Caramel 60L", 50.0, 40.0, 100.0)
        );

        initialParameters.setInitialInventory(initialInventory);

        modelController.runModel(initialParameters);
    }


}