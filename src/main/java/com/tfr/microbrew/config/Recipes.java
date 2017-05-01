package com.tfr.microbrew.config;

import com.google.common.collect.Lists;
import com.tfr.microbrew.model.Recipe;
import com.tfr.microbrew.model.RecipeIngredient;

import java.util.HashMap;
import java.util.Map;

import static com.tfr.microbrew.config.Constants.BrewHouse.BATCH_SIZE;
import static com.tfr.microbrew.config.Constants.*;

/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
public interface Recipes {

    Recipe CHECKS_AND_BALANCES_IPA = new Recipe(
            RecipeNames.CHECKS_AND_BALANCES_IPA,
            BATCH_SIZE,
            Lists.newArrayList(
                    new RecipeIngredient(Ingredients.Grain.AMERICAN_2_ROW, 250.0),
                    new RecipeIngredient(Ingredients.Grain.CARAMEL_20L, 50.0),
                    new RecipeIngredient(Ingredients.Hop.CASCADE, 10.0),
                    new RecipeIngredient(Ingredients.Hop.STYRIAN_GOLDINGS, 5.0),
                    new RecipeIngredient(Ingredients.Adjunct.IRISH_MOSS, 1.0),
                    new RecipeIngredient(Ingredients.Yeast.SAFALE_AMERICAN_ALE, 10.0)
            ),
            7,
            2
    );

    Recipe ROSIES_RED_ALE = new Recipe(
            RecipeNames.ROSIES_RED_ALE,
            BATCH_SIZE,
            Lists.newArrayList(
                    new RecipeIngredient(Ingredients.Grain.AMERICAN_2_ROW, 250.0),
                    new RecipeIngredient(Ingredients.Grain.CARAMEL_60L, 50.0),
                    new RecipeIngredient(Ingredients.Hop.KENT_GOLDINGS, 10.0),
                    new RecipeIngredient(Ingredients.Adjunct.IRISH_MOSS, 1.0),
                    new RecipeIngredient(Ingredients.Yeast.SAFALE_AMERICAN_ALE, 10.0)
            ),
            7,
            2
    );

    Recipe COLD_BREW_COFEE_PORTER = new Recipe(
            RecipeNames.COLD_BREW_COFEE_PORTER,
            BATCH_SIZE,
            Lists.newArrayList(
                    new RecipeIngredient(Ingredients.Grain.AMERICAN_2_ROW, 250.0),
                    new RecipeIngredient(Ingredients.Grain.CARAMEL_20L, 50.0),
                    new RecipeIngredient(Ingredients.Hop.CASCADE, 10.0),
                    new RecipeIngredient(Ingredients.Hop.KENT_GOLDINGS, 5.0),
                    new RecipeIngredient(Ingredients.Adjunct.IRISH_MOSS, 1.0),
                    new RecipeIngredient(Ingredients.Yeast.SAFALE_AMERICAN_ALE, 10.0)
            ),
            10,
            1
    );

    Recipe TRIPPLECANOE_AND_TYLER_TOO = new Recipe(
            RecipeNames.TRIPPLECANOE_AND_TYLER_TOO,
            BATCH_SIZE,
            Lists.newArrayList(
                    new RecipeIngredient(Ingredients.Grain.BELGIAN_2_ROW, 250.0),
                    new RecipeIngredient(Ingredients.Grain.CARAMEL_40L, 50.0),
                    new RecipeIngredient(Ingredients.Grain.BISCUIT, 50.0),
                    new RecipeIngredient(Ingredients.Hop.CITRA, 10.0),
                    new RecipeIngredient(Ingredients.Adjunct.IRISH_MOSS, 1.0),
                    new RecipeIngredient(Ingredients.Yeast.SAFALE_ENGLISH_ALE, 10.0)
            ),
            12,
            2
    );

    Recipe WIT_OF_THEIR_EYES = new Recipe(
            RecipeNames.WIT_OF_THEIR_EYES,
            BATCH_SIZE,
            Lists.newArrayList(
                    new RecipeIngredient(Ingredients.Grain.PILSEN_2_ROW, 250.0),
                    new RecipeIngredient(Ingredients.Grain.CARAMEL_20L, 50.0),
                    new RecipeIngredient(Ingredients.Hop.CASCADE, 10.0),
                    new RecipeIngredient(Ingredients.Adjunct.IRISH_MOSS, 1.0),
                    new RecipeIngredient(Ingredients.Yeast.WHITELABS_WITBIER, 10.0)
            ),
            5,
            2
    );


    Recipe AMBER_WAVES_OF_GRAIN = new Recipe(
            RecipeNames.AMBER_WAVES_OF_GRAIN,
            BATCH_SIZE,
            Lists.newArrayList(
                    new RecipeIngredient(Ingredients.Grain.AMERICAN_2_ROW, 250.0),
                    new RecipeIngredient(Ingredients.Grain.CARAMEL_20L, 50.0),
                    new RecipeIngredient(Ingredients.Hop.CASCADE, 10.0),
                    new RecipeIngredient(Ingredients.Adjunct.IRISH_MOSS, 1.0),
                    new RecipeIngredient(Ingredients.Yeast.SAFALE_AMERICAN_ALE, 10.0)
            ),
            7,
            2
    );


    Recipe SUMMER_SMASH_IPA = new Recipe(
            RecipeNames.SUMMER_SMASH_IPA,
            BATCH_SIZE,
            Lists.newArrayList(
                    new RecipeIngredient(Ingredients.Grain.AMERICAN_2_ROW, 250.0),
                    new RecipeIngredient(Ingredients.Grain.CARAMEL_20L, 50.0),
                    new RecipeIngredient(Ingredients.Hop.CASCADE, 10.0),
                    new RecipeIngredient(Ingredients.Hop.CITRA, 10.0),
                    new RecipeIngredient(Ingredients.Adjunct.IRISH_MOSS, 1.0),
                    new RecipeIngredient(Ingredients.Yeast.SAFALE_AMERICAN_ALE, 10.0)
            ),
            7,
            2
    );

    Map<String, Recipe> RECIPES = new HashMap<String, Recipe>() {{
        put(CHECKS_AND_BALANCES_IPA.getName(), CHECKS_AND_BALANCES_IPA);
        put(ROSIES_RED_ALE.getName(), ROSIES_RED_ALE);
        put(COLD_BREW_COFEE_PORTER.getName(), COLD_BREW_COFEE_PORTER);
        put(TRIPPLECANOE_AND_TYLER_TOO.getName(), TRIPPLECANOE_AND_TYLER_TOO);
        put(WIT_OF_THEIR_EYES.getName(), WIT_OF_THEIR_EYES);
        put(AMBER_WAVES_OF_GRAIN.getName(), AMBER_WAVES_OF_GRAIN);
        put(SUMMER_SMASH_IPA.getName(), SUMMER_SMASH_IPA);
    }};

}
