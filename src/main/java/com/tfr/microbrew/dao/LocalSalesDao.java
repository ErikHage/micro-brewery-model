package com.tfr.microbrew.dao;

import com.tfr.microbrew.model.Sale;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * Created by Erik on 4/28/2017.
 */
@Repository("LocalSalesDao")
public class LocalSalesDao implements SalesDao {

    private static final Map<LocalDate, List<Sale>> SALES = new HashMap<>();

    @Override
    public void create(Sale sale) {
        SALES.putIfAbsent(sale.getDateOfSale(), new ArrayList<>());
        SALES.get(sale.getDateOfSale()).add(sale);
    }

    @Override
    public List<Sale> readAll() {
        return SALES.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> readByDate(LocalDate date) {
        if(!SALES.containsKey(date)) {
            return Collections.emptyList();
        }
        return SALES.get(date);
    }

    @Override
    public List<Sale> readByFulfilled(boolean isFulfilled) {
        return SALES.values().stream()
                .flatMap(Collection::stream)
                .filter(s -> s.isFulfilled() == isFulfilled)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> readByFulfilled(boolean isFulfilled, LocalDate date) {
        return SALES.get(date)
                .stream()
                .filter(s -> s.isFulfilled() == isFulfilled)
                .collect(Collectors.toList());
    }
}
