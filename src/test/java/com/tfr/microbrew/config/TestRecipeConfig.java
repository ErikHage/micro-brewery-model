package com.tfr.microbrew.config;

import com.tfr.microbrew.MicroBreweryModelApplication;
import com.tfr.microbrew.model.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MicroBreweryModelApplication.class)
public class TestRecipeConfig {

    @Resource(name = "RecipesList")
    private List<Recipe> recipes;

    @Test
    public void testRecipeConfigLoads() {
        assertNotNull(recipes);
        assertTrue(recipes.size() > 0);
    }

    @Test
    public void testRecipeConfig_ContainsChecksAndBalances() {
        assertNotNull(recipes.get(0));
        Recipe recipe = recipes.get(0);
        assertEquals("Checks And Balances IPA",recipe.getName());
    }


}
