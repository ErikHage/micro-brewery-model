package com.tfr.microbrew.config;

import com.tfr.microbrew.MicroBreweryModelApplication;
import com.tfr.microbrew.model.BeverageProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = MicroBreweryModelApplication.class)
public class TestSalesConfig {

    @Autowired
    @Qualifier("ProductProbabilities")
    private Map<String, Double> productProbabilities;

    @Test
    public void testProductProbabilityLoads() {
        assertNotNull(productProbabilities);
    }

    @Test
    public void testProductProbability_ValuesPopulated() {
        assertNotNull(productProbabilities.get("C"));
    }

    @Autowired
    @Qualifier("VolumeProbabilities")
    private List<BeverageProduct> volumeProbabilities;

    @Test
    public void testVolumeProbabilities() {
        assertNotNull(volumeProbabilities);
    }

    @Test
    public void testVolumeProbabilities_ValuesPopulated() {
        assertNotNull(volumeProbabilities.get(0));
        BeverageProduct product = volumeProbabilities.get(0);
        assertEquals(BeverageVolume.PINT, product.getBeverageVolume());
        assertEquals(0.125, product.getVolume(), 0.01);
        assertEquals(75.0, product.getProbability(), 0.01);
    }



}
