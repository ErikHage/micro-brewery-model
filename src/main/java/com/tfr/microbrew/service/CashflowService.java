package com.tfr.microbrew.service;

import com.tfr.microbrew.model.Cashflow;
import org.joda.time.LocalDate;

import java.util.List;

/**
 *
 * Created by Erik on 5/19/2017.
 */
public interface CashflowService {

    void saveCashflow(Cashflow cashflow);

    List<Cashflow> getAll();
    List<Cashflow> getByDate(LocalDate date);

}
