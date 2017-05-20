package com.tfr.microbrew.service;

import com.tfr.microbrew.dao.CashflowDao;
import com.tfr.microbrew.model.Cashflow;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by Erik on 5/19/2017.
 */
@Service("CashflowServiceImpl")
public class CashflowServiceImpl implements CashflowService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CashflowDao cashflowDao;

    @Autowired
    public CashflowServiceImpl(CashflowDao cashflowDao) {
        this.cashflowDao = cashflowDao;
    }

    @Override
    public void saveCashflow(Cashflow cashflow) {
        cashflowDao.create(cashflow);
    }

    @Override
    public List<Cashflow> getAll() {
        return cashflowDao.readAll();
    }

    @Override
    public List<Cashflow> getByDate(LocalDate date) {
        return cashflowDao.readByDate(date);
    }
}
