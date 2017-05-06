package com.tfr.microbrew.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.tfr.microbrew.model.BeverageProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * Created by Erik on 4/24/2017.
 */
@Configuration
public class SalesConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String PRODUCT_DATA_FILE = "config/salesProduct.json";
    private static final String VOLUME_DATA_FILE = "config/salesVolume.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public final static int MAX_SALES = 200;
    public final static int MIN_SALES = 200;

    public final static double WEEKEND_FACTOR = 1.5;

    @Bean(name="ProductProbabilities")
    public Map<String, Double> productProbabilities() {
        Map<String, Double> probabilityMap;

        try {
            URL url = Resources.getResource(PRODUCT_DATA_FILE);
            String json = Resources.toString(url, Charsets.UTF_8);
            probabilityMap = objectMapper.readValue(json, new TypeReference<Map<String, Double>>(){});
            logger.debug("Loaded product sale probabilities from " + PRODUCT_DATA_FILE);
            return probabilityMap;
        } catch(IOException ex) {
            logger.error("Error reading config file " + PRODUCT_DATA_FILE, ex);
            throw new RuntimeException("Error reading config file: " + PRODUCT_DATA_FILE);
        }
    }

    @Bean(name="VolumeProbabilities")
    public List<BeverageProduct> volumeProbabilities() {
        List<BeverageProduct> probs;

        try {
            URL url = Resources.getResource(VOLUME_DATA_FILE);
            String json = Resources.toString(url, Charsets.UTF_8);
            probs = objectMapper.readValue(json, new TypeReference<List<BeverageProduct>>(){});
            logger.debug("Loaded product volume probabilities from " + VOLUME_DATA_FILE);
            return probs;
        } catch(IOException ex) {
            logger.error("Error reading config file " + VOLUME_DATA_FILE, ex);
            throw new RuntimeException("Error reading config file: " + VOLUME_DATA_FILE);
        }
    }

}
