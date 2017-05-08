package com.tfr.microbrew.config;

import com.tfr.microbrew.model.BeverageProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tfr.microbrew.config.BeverageVolume.*;
import static com.tfr.microbrew.config.Constants.RecipeNames.*;

/**
 *
 *
 * Created by Erik on 4/24/2017.
 */
@Configuration
public class SalesConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public final static int MAX_SALES = 200;
    public final static int MIN_SALES = 200;

    public final static double WEEKEND_FACTOR = 1.5;

    @Bean(name="ProductProbabilities")
    public Map<String, Double> productProbabilities() {
        Map<String, Double> probabilityMap = new HashMap<>();

        probabilityMap.put(CHECKS_AND_BALANCES_IPA, 30.0);
        probabilityMap.put(ROSIES_RED_ALE, 15.0);
        probabilityMap.put(COLD_BREW_COFFEE_PORTER, 15.0);
        probabilityMap.put(TRIPPLECANOE_AND_TYLER_TOO, 10.0);
        probabilityMap.put(WIT_OF_THEIR_EYES, 10.0);
        probabilityMap.put(AMBER_WAVES_OF_GRAIN, 10.0);
        probabilityMap.put(SUMMER_SMASH_IPA, 10.0);

        logger.debug("Loaded product sale probabilities.");
        return probabilityMap;
    }

    @Bean(name="VolumeProbabilities")
    public List<BeverageProduct> volumeProbabilities() {
        List<BeverageProduct> probabilities = new ArrayList<>();

        probabilities.add(new BeverageProduct(PINT, 0.125, 6.00, 75.0));
        probabilities.add(new BeverageProduct(SAMPLE, 0.0625, 3.00, 10.0));
        probabilities.add(new BeverageProduct(FLIGHT, 0.25, 6.00, 7.0));
        probabilities.add(new BeverageProduct(HOWLER, 0.25, 6.00, 5.0));
        probabilities.add(new BeverageProduct(GROWLER, 0.5, 6.00, 3.0));

        logger.debug("Loaded product volume probabilities");
        return probabilities;

    }

}
