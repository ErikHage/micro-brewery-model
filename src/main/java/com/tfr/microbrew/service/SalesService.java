package com.tfr.microbrew.service;

import com.tfr.microbrew.model.Sale;

import java.util.List;

/**
 *
 * Created by Erik on 4/24/2017.
 */
public interface SalesService {

    Sale generateSale();
    List<Sale> generateSales(int num);

    void saveSale(Sale sale);

}
