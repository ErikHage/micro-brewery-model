package com.tfr.microbrew.service;

import com.tfr.microbrew.model.Sale;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 *
 * Created by Erik on 4/24/2017.
 */
public interface SalesService {

    Sale generateSale();
    List<Sale> generateSales(int num);

    void performSale(Sale sale);

    int getSales(boolean isFulfilled);

    List<Sale> getSales();
    List<Sale> getSalesByProduct(String productName);
    List<Sale> getSalesByFulfillment(boolean isFulfilled);
    List<Sale> getSalesByDate(LocalDate date);

    Map<String, Long> getUnfulfilledSalesByProduct();

}
