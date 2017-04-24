package com.tfr.microbrew.config;

import com.google.common.collect.Sets;
import com.tfr.microbrew.model.InventoryItem;

import java.util.Set;

/**
 *
 *
 * Created by Erik on 4/23/2017.
 */
public interface InventoryConfig {

    Set<InventoryItem> DEFAULT_ITEMS = Sets.newHashSet(
        //Beers
        new InventoryItem(Constants.RecipeNames.CHECKS_AND_BALANCES_IPA, Categories.BEER, 0, 100, Constants.BrewHouse.BATCH_SIZE),
        new InventoryItem(Constants.RecipeNames.ROSIES_RED_ALE, Categories.BEER, 0, 80, Constants.BrewHouse.BATCH_SIZE),
        new InventoryItem(Constants.RecipeNames.COLD_BREW_COFEE_PORTER, Categories.BEER, 0, 60, Constants.BrewHouse.BATCH_SIZE),
        new InventoryItem(Constants.RecipeNames.TRIPPLECANOE_AND_TYLER_TOO, Categories.BEER, 0, 40, Constants.BrewHouse.BATCH_SIZE),
        new InventoryItem(Constants.RecipeNames.WIT_OF_THEIR_EYES, Categories.BEER, 0, 60, Constants.BrewHouse.BATCH_SIZE),
        new InventoryItem(Constants.RecipeNames.AMBER_WAVES_OF_GRAIN, Categories.BEER, 0, 60, Constants.BrewHouse.BATCH_SIZE),
        new InventoryItem(Constants.RecipeNames.SUMMER_SMASH_IPA, Categories.BEER, 0, 60, Constants.BrewHouse.BATCH_SIZE)

//        //Grains
//        new InventoryItem("", Categories.GRAIN, 0, 50, 100),
//        new InventoryItem("", Categories.GRAIN, 0, 50, 100),
//        new InventoryItem("", Categories.GRAIN, 0, 50, 100),
//        new InventoryItem("", Categories.GRAIN, 0, 50, 100),
//        new InventoryItem("", Categories.GRAIN, 0, 50, 100),
//        new InventoryItem("", Categories.GRAIN, 0, 50, 100),
//
//        //Hops
//        new InventoryItem("", Categories.HOP, 0, 50, 100),
//        new InventoryItem("", Categories.HOP, 0, 50, 100),
//        new InventoryItem("", Categories.HOP, 0, 50, 100),
//        new InventoryItem("", Categories.HOP, 0, 50, 100),
//        new InventoryItem("", Categories.HOP, 0, 50, 100),
//        new InventoryItem("", Categories.HOP, 0, 50, 100),
//
//        //Adjuncts
//        new InventoryItem("", Categories.ADJUNCT, 0, 50, 100),
//        new InventoryItem("", Categories.ADJUNCT, 0, 50, 100),
//        new InventoryItem("", Categories.ADJUNCT, 0, 50, 100),
//        new InventoryItem("", Categories.ADJUNCT, 0, 50, 100),
//        new InventoryItem("", Categories.ADJUNCT, 0, 50, 100),
//        new InventoryItem("", Categories.ADJUNCT, 0, 50, 100),
//
//        //Yeast
//        new InventoryItem("", Categories.YEAST, 0, 50, 100),
//        new InventoryItem("", Categories.YEAST, 0, 50, 100),
//        new InventoryItem("", Categories.YEAST, 0, 50, 100),
//        new InventoryItem("", Categories.YEAST, 0, 50, 100),
//        new InventoryItem("", Categories.YEAST, 0, 50, 100),
//        new InventoryItem("", Categories.YEAST, 0, 50, 100)

    );

    interface Categories {

        String GRAIN = "Grain";
        String HOP = "Hop";
        String YEAST = "Yeast";
        String ADJUNCT = "Adjunct";

        String BEER = "Beer";

    }

}
