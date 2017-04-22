package com.tfr.microbrew.config;

import com.tfr.microbrew.model.Recipe;

import java.util.HashMap;
import java.util.Map;

import static com.tfr.microbrew.config.Constants.BrewHouse.BATCH_SIZE;
import static com.tfr.microbrew.config.Constants.RecipeNames.*;

/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
public interface Recipes {

    Map<String, Recipe> RECIPES = new HashMap<String, Recipe>() {{
        put(CHECKS_AND_BALANCES_IPA, new Recipe(CHECKS_AND_BALANCES_IPA, BATCH_SIZE, 7, 2));
        put(ROSIES_RED_ALE, new Recipe(ROSIES_RED_ALE, BATCH_SIZE, 7, 2));
        put(COLD_BREW_COFEE_PORTER, new Recipe(COLD_BREW_COFEE_PORTER, BATCH_SIZE, 10, 1));
        put(TRIPPLECANOE_AND_TYLER_TOO, new Recipe(TRIPPLECANOE_AND_TYLER_TOO, BATCH_SIZE, 12, 2));
        put(WIT_OF_THEIR_EYES, new Recipe(WIT_OF_THEIR_EYES, BATCH_SIZE, 5, 2));
        put(AMBER_WAVES_OF_GRAIN, new Recipe(AMBER_WAVES_OF_GRAIN, BATCH_SIZE, 7, 2));
        put(SUMMER_SMASH_IPA, new Recipe(SUMMER_SMASH_IPA, BATCH_SIZE, 7, 2));
    }};

}
