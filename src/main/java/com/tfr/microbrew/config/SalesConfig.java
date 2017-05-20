package com.tfr.microbrew.config;

import com.google.common.collect.Lists;
import com.tfr.microbrew.model.BeverageProduct;
import com.tfr.microbrew.model.Recipe;
import com.tfr.microbrew.probability.NormalizedProbability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tfr.microbrew.config.BeverageVolume.*;

/**
 *
 *
 * Created by Erik on 4/24/2017.
 */
@Configuration
public class SalesConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final List<Recipe> recipes;

    public final static int MAX_SALES = 200;
    public final static int MIN_SALES = 200;

    public final static double WEEKEND_FACTOR = 1.5;

    @Autowired
    public SalesConfig(@Qualifier("RecipesList") List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Bean(name="ProductProbabilities")
    public NormalizedProbability<Recipe> productProbabilities() {
        NormalizedProbability<Recipe> productProbability =
                new NormalizedProbability<>();

        recipes.forEach(r -> {
            productProbability.add(r.getSaleProbability(), r);
            logger.debug(String.format("Probability of sale of product: %-6s= %s",
                    r.getSaleProbability(), r.getName()));
        });

        return productProbability;
    }

    @Bean(name="BeverageProducts")
    public List<BeverageProduct> beverageProducts() {
        return Lists.newArrayList(
                new BeverageProduct(PINT, 0.125, 75.0),
                new BeverageProduct(SAMPLE, 0.0625, 10.0),
                new BeverageProduct(FLIGHT, 0.25, 7.0),
                new BeverageProduct(HOWLER, 0.25, 5.0),
                new BeverageProduct(GROWLER, 0.5, 3.0)
        );
    }

    @Bean(name="VolumeProbabilities")
    public NormalizedProbability<BeverageProduct> volumeProbabilities() {
        NormalizedProbability<BeverageProduct> volumeProbability =
                new NormalizedProbability<>();

        beverageProducts().forEach(p -> {
            volumeProbability.add(p.getProbability(), p);
            logger.debug(String.format("Probability of sale of volume: %-6s= %s",
                    p.getProbability(), p.getBeverageVolume().getValue()));
        });

        logger.debug("Loaded product volume probabilities");
        return volumeProbability;
    }

    @Bean(name="VolumeFactors")
    public Map<BeverageVolume, Double> volumeFactors() {
        Map<BeverageVolume, Double> volumeFactors = new HashMap<>();

        volumeFactors.put(PINT, 1.0);
        volumeFactors.put(SAMPLE, 3.0/6.0);
        volumeFactors.put(FLIGHT, 10.0/6.0);
        volumeFactors.put(HOWLER, 10.0/6.0);
        volumeFactors.put(GROWLER, 16.0/6.0);

        return volumeFactors;
    }

}
