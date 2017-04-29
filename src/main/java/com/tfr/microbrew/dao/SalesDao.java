package com.tfr.microbrew.dao;

import com.tfr.microbrew.model.Sale;
import org.joda.time.LocalDate;

import java.util.List;

/**
 *
 *
 * Created by Erik on 4/28/2017.
 */
public interface SalesDao {

    void create(Sale sale);

    List<Sale> readAll();
    List<Sale> readByDate(LocalDate date);
    List<Sale> readByFulfilled(boolean isFulfilled);
    List<Sale> readByFulfilled(boolean isFulfilled, LocalDate date);

}
