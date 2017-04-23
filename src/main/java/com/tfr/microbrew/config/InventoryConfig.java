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
           new InventoryItem(Constants.RecipeNames.CHECKS_AND_BALANCES_IPA, 0, 100, Constants.BrewHouse.BATCH_SIZE),
           new InventoryItem(Constants.RecipeNames.ROSIES_RED_ALE, 0, 80, Constants.BrewHouse.BATCH_SIZE),
           new InventoryItem(Constants.RecipeNames.COLD_BREW_COFEE_PORTER, 0, 60, Constants.BrewHouse.BATCH_SIZE),
           new InventoryItem(Constants.RecipeNames.TRIPPLECANOE_AND_TYLER_TOO, 0, 40, Constants.BrewHouse.BATCH_SIZE),
           new InventoryItem(Constants.RecipeNames.WIT_OF_THEIR_EYES, 0, 60, Constants.BrewHouse.BATCH_SIZE),
           new InventoryItem(Constants.RecipeNames.AMBER_WAVES_OF_GRAIN, 0, 60, Constants.BrewHouse.BATCH_SIZE),
           new InventoryItem(Constants.RecipeNames.SUMMER_SMASH_IPA, 0, 60, Constants.BrewHouse.BATCH_SIZE)
   );

}
