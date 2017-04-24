package com.tfr.microbrew.config;

import com.tfr.microbrew.model.BeverageProduct;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * Created by Erik on 4/24/2017.
 */
public interface SalesConfig {

    int MAX_SALES = 200;
    int MIN_SALES = 200;

    double WEEKEND_FACTOR = 1.5;

    Map<BeverageVolume, BeverageProduct> BEVERAGE_PRODUCTS = new HashMap<BeverageVolume, BeverageProduct>() {{
        put(BeverageProducts.PINT.getBeverageVolume(), BeverageProducts.PINT);
        put(BeverageProducts.SAMPLE.getBeverageVolume(), BeverageProducts.SAMPLE);
        put(BeverageProducts.FLIGHT.getBeverageVolume(), BeverageProducts.FLIGHT);
        put(BeverageProducts.HOWLER.getBeverageVolume(), BeverageProducts.HOWLER);
        put(BeverageProducts.GROWLER.getBeverageVolume(), BeverageProducts.GROWLER);
    }};

    interface BeverageProducts {
        BeverageProduct PINT = new BeverageProduct(BeverageVolume.PINT, 0.125, 6.00, 75.0);
        BeverageProduct SAMPLE = new BeverageProduct(BeverageVolume.SAMPLE, 0.0625, 3.00, 10.0);
        BeverageProduct FLIGHT = new BeverageProduct(BeverageVolume.FLIGHT, 0.250, 10.00, 5.0);
        BeverageProduct HOWLER = new BeverageProduct(BeverageVolume.HOWLER, 0.250, 9.00, 7.0);
        BeverageProduct GROWLER = new BeverageProduct(BeverageVolume.GROWLER, 0.500, 16.00, 3.0);
    }

}
