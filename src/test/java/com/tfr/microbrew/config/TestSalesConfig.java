package com.tfr.microbrew.config;

import com.tfr.microbrew.model.BeverageProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * Created by Erik Hage on 5/6/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
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
