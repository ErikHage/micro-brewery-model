package com.tfr.microbrew.dao;

import com.tfr.microbrew.model.Cashflow;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Created by Erik on 5/19/2017.
 */
@Repository("LocalCashflowDao")
public class LocalCashflowDao implements CashflowDao {

    private static final List<Cashflow> CASHFLOWS = new ArrayList<>();

    @Override
    public void create(Cashflow cashflow) {
        CASHFLOWS.add(cashflow);
    }

    @Override
    public List<Cashflow> readAll() {
        return CASHFLOWS;
    }

    @Override
    public List<Cashflow> readByDate(LocalDate date) {
        return CASHFLOWS.stream()
                .filter(c -> c.getDate().equals(date))
                .collect(Collectors.toList());
    }
}
