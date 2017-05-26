package com.tfr.microbrew.processor;

import com.google.common.collect.Sets;
import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.model.Cashflow;
import com.tfr.microbrew.service.CashflowService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 *
 * Created by Erik Hage on 5/26/2017.
 */
@Component("OverheadProcessor")
public class OverheadProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CashflowService cashflowService;

    @Autowired
    public OverheadProcessor(CashflowService cashflowService) {
        this.cashflowService = cashflowService;
    }

    @Override
    public void process(LocalDate date) {
        int dayOfMonth = date.getDayOfMonth();

        if(dayOfMonth == 1) {
            logger.debug("Rent on first of the month");
            cashflowService.saveCashflow(new Cashflow(date, -5000.00));

            logger.debug("Other monthly costs");
            cashflowService.saveCashflow(new Cashflow(date, -2000.00));
        }

        if(dayOfMonth == 15) {
            logger.debug("Utilities due on 15th of the month");
            cashflowService.saveCashflow(new Cashflow(date, -2000.00));
        }
    }

    @Override
    public Set<DayOfWeek> getDaysToProcess() {
        return Sets.newHashSet(DayOfWeek.values());
    }

    @Override
    public String getName() {
        return "OverheadProcessor";
    }
}
