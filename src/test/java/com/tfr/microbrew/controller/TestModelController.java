package com.tfr.microbrew.controller;

import com.tfr.microbrew.config.Ingredients;
import com.tfr.microbrew.model.InitialParameters;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

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

        Map<String, Double> initialInventory = new HashMap<String, Double>() {{
            put(RecipeNames.CHECKS_AND_BALANCES_IPA, 150.0);
            put(RecipeNames.ROSIES_RED_ALE, 93.0);
            put(RecipeNames.COLD_BREW_COFFEE_PORTER, 93.0);
            put(RecipeNames.TRIPPLECANOE_AND_TYLER_TOO, 93.0);
            put(RecipeNames.WIT_OF_THEIR_EYES, 93.0);
            put(RecipeNames.AMBER_WAVES_OF_GRAIN, 93.0);
            put(RecipeNames.SUMMER_SMASH_IPA, 93.0);

            put(Ingredients.Grain.AMERICAN_2_ROW, 200.0);
            put(Ingredients.Grain.CARAMEL_60L, 50.0);

            put(Ingredients.Hop.CASCADE, 50.0);
            put(Ingredients.Hop.CITRA, 50.0);

            put(Ingredients.Yeast.SAFALE_AMERICAN_ALE, 50.0);

            put(Ingredients.Adjunct.IRISH_MOSS, 50.0);
            put(Ingredients.Adjunct.LIGHT_CANDI_SUGAR, 50.0);
            put(Ingredients.Adjunct.DARK_CANDI_SUGAR, 50.0);
        }};

        initialParameters.setInitialInventory(initialInventory);

        modelController.runModel(initialParameters);
    }


}
