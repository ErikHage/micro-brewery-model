package com.tfr.microbrew.dao;

import com.tfr.microbrew.model.Cashflow;
import org.joda.time.LocalDate;

import java.util.List;

/**
 *
 * Created by Erik on 5/19/2017.
 */
public interface CashflowDao {

    void create(Cashflow cashflow);

    List<Cashflow> readAll();
    List<Cashflow> readByDate(LocalDate date);

}
