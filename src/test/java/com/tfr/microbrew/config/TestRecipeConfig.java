package com.tfr.microbrew.config;

import com.tfr.microbrew.model.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * Created by Erik Hage on 5/4/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
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
