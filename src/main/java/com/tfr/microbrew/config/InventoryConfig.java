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
        new InventoryItem(Constants.RecipeNames.SUMMER_SMASH_IPA, Categories.BEER, 0, 60, Constants.BrewHouse.BATCH_SIZE),

        //Grains
        new InventoryItem(Ingredients.Grain.AMERICAN_2_ROW, Categories.GRAIN, 0, 500, 1200),
        new InventoryItem(Ingredients.Grain.BELGIAN_2_ROW, Categories.GRAIN, 0, 500, 1200),
        new InventoryItem(Ingredients.Grain.PILSEN_2_ROW, Categories.GRAIN, 0, 500, 1200),
        new InventoryItem(Ingredients.Grain.CARAMEL_20L, Categories.GRAIN, 0, 150, 500),
        new InventoryItem(Ingredients.Grain.CARAMEL_40L, Categories.GRAIN, 0, 150, 500),
        new InventoryItem(Ingredients.Grain.CARAMEL_60L, Categories.GRAIN, 0, 150, 500),
        new InventoryItem(Ingredients.Grain.BISCUIT, Categories.GRAIN, 0, 100, 250),

        //Hops
        new InventoryItem(Ingredients.Hop.STYRIAN_GOLDINGS, Categories.HOP, 0, 50, 100),
        new InventoryItem(Ingredients.Hop.KENT_GOLDINGS, Categories.HOP, 0, 50, 100),
        new InventoryItem(Ingredients.Hop.CASCADE, Categories.HOP, 0, 50, 150),
        new InventoryItem(Ingredients.Hop.CITRA, Categories.HOP, 0, 20, 50),

        //Adjuncts
        new InventoryItem(Ingredients.Adjunct.IRISH_MOSS, Categories.ADJUNCT, 0, 5, 30),
        new InventoryItem(Ingredients.Adjunct.LIGHT_CANDI_SUGAR, Categories.ADJUNCT, 0, 50, 100),
        new InventoryItem(Ingredients.Adjunct.DARK_CANDI_SUGAR, Categories.ADJUNCT, 0, 50, 100),

        //Yeast
        new InventoryItem(Ingredients.Yeast.WHITELABS_SAISON, Categories.YEAST, 0, 10, 30),
        new InventoryItem(Ingredients.Yeast.WHITELABS_WITBIER, Categories.YEAST, 0, 10, 30),
        new InventoryItem(Ingredients.Yeast.SAFALE_AMERICAN_ALE, Categories.YEAST, 0, 10, 30),
        new InventoryItem(Ingredients.Yeast.SAFALE_ENGLISH_ALE, Categories.YEAST, 0, 10, 30)

    );

    interface Categories {

        String GRAIN = "Grain";
        String HOP = "Hop";
        String YEAST = "Yeast";
        String ADJUNCT = "Adjunct";

        String BEER = "Beer";

    }

}
