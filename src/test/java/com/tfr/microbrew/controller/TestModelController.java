package com.tfr.microbrew.controller;

import com.google.common.collect.Sets;
import com.tfr.microbrew.config.Constants;
import com.tfr.microbrew.config.InventoryConfig;
import com.tfr.microbrew.model.InitialParameters;
import com.tfr.microbrew.model.InventoryItem;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static com.tfr.microbrew.config.Constants.*;

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
                new InventoryItem("American 2 Row", InventoryConfig.Categories.GRAIN, 200, 100, 500),
                new InventoryItem("Caramel 60L", InventoryConfig.Categories.GRAIN, 50, 40, 100),

                new InventoryItem("Cascade", InventoryConfig.Categories.HOP, 50, 40, 100),
                new InventoryItem("Citra", InventoryConfig.Categories.HOP, 50, 40, 100),

                new InventoryItem("White Labs 4100", InventoryConfig.Categories.YEAST, 50, 40, 100),

                new InventoryItem("Super Moss", InventoryConfig.Categories.ADJUNCT, 50, 40, 100),
                new InventoryItem("Light Belgian Candi Sugar", InventoryConfig.Categories.ADJUNCT, 50, 40, 100),

                new InventoryItem(RecipeNames.CHECKS_AND_BALANCES_IPA, InventoryConfig.Categories.BEER, 93, 100, BrewHouse.BATCH_SIZE),
                new InventoryItem(RecipeNames.ROSIES_RED_ALE, InventoryConfig.Categories.BEER, 93, 80, BrewHouse.BATCH_SIZE),
                new InventoryItem(RecipeNames.COLD_BREW_COFEE_PORTER, InventoryConfig.Categories.BEER, 93, 80, BrewHouse.BATCH_SIZE),
                new InventoryItem(RecipeNames.TRIPPLECANOE_AND_TYLER_TOO, InventoryConfig.Categories.BEER, 93, 80, BrewHouse.BATCH_SIZE),
                new InventoryItem(RecipeNames.WIT_OF_THEIR_EYES, InventoryConfig.Categories.BEER, 93, 80, BrewHouse.BATCH_SIZE),
                new InventoryItem(RecipeNames.AMBER_WAVES_OF_GRAIN, InventoryConfig.Categories.BEER, 93, 80, BrewHouse.BATCH_SIZE),
                new InventoryItem(RecipeNames.SUMMER_SMASH_IPA, InventoryConfig.Categories.BEER, 93, 80, BrewHouse.BATCH_SIZE)
        );

        initialParameters.setInitialInventory(initialInventory);

        modelController.runModel(initialParameters);
    }


}
