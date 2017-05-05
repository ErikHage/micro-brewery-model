package com.tfr.microbrew.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.tfr.microbrew.model.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 *
 * Created by Erik Hage on 5/4/2017.
 */
@Configuration
public class RecipeConfig {

    private static final String DATA_FILE = "config/recipes.json";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean(name = "RecipesList")
    public List<Recipe> recipesList() {
        List<Recipe> recipes;

        try {
            URL url = Resources.getResource(DATA_FILE);
            String json = Resources.toString(url, Charsets.UTF_8);
            recipes = objectMapper.readValue(json, new TypeReference<List<Recipe>>(){});
            logger.debug("Loaded recipe data from " + DATA_FILE);
            return recipes;
        }
        catch(IOException ex) {
            logger.error("Error reading config file " + DATA_FILE, ex);
            throw new RuntimeException("Error reading config file: " + DATA_FILE);
        }
    }


}
