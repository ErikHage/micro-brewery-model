package com.tfr.microbrew.service;

import com.tfr.microbrew.config.Constants;
import com.tfr.microbrew.config.SalesConfig;
import com.tfr.microbrew.model.BeverageProduct;
import com.tfr.microbrew.model.Sale;
import com.tfr.microbrew.probability.NormalizedProbability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Erik on 4/24/2017.
 */
@Service("SalesServiceImpl")
public class SalesServiceImpl implements SalesService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final List<Sale> SALES = new ArrayList<>();

    private NormalizedProbability<BeverageProduct> volumeProbability;
    private NormalizedProbability<String> productProbability;

    public SalesServiceImpl() {
        this.volumeProbability = new NormalizedProbability<>();
        this.productProbability = new NormalizedProbability<>();
        init();
    }

    private void init() {
        SalesConfig.BEVERAGE_PRODUCTS.entrySet().forEach(e -> {
            volumeProbability.add(e.getValue().getProbability(), e.getValue());
            logger.debug(String.format("Probability of sale of volume: %-7s=%s", e.getKey(), e.getValue()));
        });
        //update this later to weight staples higher
        Constants.ACTIVE_PRODUCTS.forEach(p -> productProbability.add(10.0, p));
    }

    @Override
    public Sale generateSale() {
        BeverageProduct beverageProduct = volumeProbability.getRandom();
        String beerName = productProbability.getRandom();
        return new Sale(beverageProduct, beerName);
    }

    @Override
    public List<Sale> generateSales(int num) {
        List<Sale> sales = new ArrayList<>();
        for(int i=0; i<num; i++) {
            sales.add(generateSale());
        }
        return sales;
    }

    @Override
    public void saveSale(Sale sale) {
        SALES.add(sale);
    }
}
