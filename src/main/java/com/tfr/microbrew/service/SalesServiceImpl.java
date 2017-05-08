package com.tfr.microbrew.service;

import com.tfr.microbrew.config.Constants;
import com.tfr.microbrew.config.SalesConfig;
import com.tfr.microbrew.dao.SalesDao;
import com.tfr.microbrew.model.BeverageProduct;
import com.tfr.microbrew.model.Sale;
import com.tfr.microbrew.probability.NormalizedProbability;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 *
 * Created by Erik on 4/24/2017.
 */
@Service("SalesServiceImpl")
public class SalesServiceImpl implements SalesService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private NormalizedProbability<BeverageProduct> volumeProbability;
    private NormalizedProbability<String> productProbability;

    private SalesDao salesDao;

    @Autowired
    public SalesServiceImpl(SalesDao salesDao,
                            @Qualifier("ProductProbabilities") Map<String, Double> productProbabilityConfig,
                            @Qualifier("VolumeProbabilities") List<BeverageProduct> volumeProbabilityConfig) {
        this.salesDao = salesDao;
        this.volumeProbability = new NormalizedProbability<>();
        this.productProbability = new NormalizedProbability<>();
        init(productProbabilityConfig, volumeProbabilityConfig);
    }

    private void init(Map<String, Double> productProbabilityConfig,
                      List<BeverageProduct> volumeProbabilityConfig) {
        volumeProbabilityConfig.forEach(b -> {
            volumeProbability.add(b.getProbability(), b);
            logger.debug(String.format("Probability of sale of volume: %-7s=%s",
                    b.getBeverageVolume().getValue(), b.getProbability()));
        });
        productProbabilityConfig.entrySet().stream()
                .filter(e -> Constants.ACTIVE_PRODUCTS.contains(e.getKey()))
                .forEach(e -> productProbability.add(e.getValue(), e.getKey()));
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
    public void performSale(Sale sale) {
        salesDao.create(sale);
    }

    @Override
    public int getSales(boolean isFulfilled) {
        return salesDao.readByFulfilled(isFulfilled).size();
    }

    @Override
    public List<Sale> getSales() {
        return salesDao.readAll();
    }

    @Override
    public List<Sale> getSalesByProduct(String productName) {
        return salesDao.readAll().stream()
                .filter(s -> s.getProductName().equals(productName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> getSalesByFulfillment(boolean isFulfilled) {
        return salesDao.readByFulfilled(isFulfilled);
    }

    @Override
    public List<Sale> getSalesByDate(LocalDate date) {
        return salesDao.readByDate(date);
    }

    @Override
    public Map<String, Long> getUnfulfilledSalesByProduct() {
        return salesDao.readByFulfilled(false)
                .stream()
                .collect(Collectors.groupingBy(Sale::getProductName, Collectors.counting()));
    }


}
