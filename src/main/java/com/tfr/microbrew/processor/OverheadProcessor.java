package com.tfr.microbrew.processor;

import com.google.common.collect.Sets;
import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.model.Cashflow;
import com.tfr.microbrew.model.FixedCost;
import com.tfr.microbrew.service.CashflowService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 *
 * Created by Erik Hage on 5/26/2017.
 */
@Component("OverheadProcessor")
public class OverheadProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CashflowService cashflowService;

    private final List<FixedCost> fixedCosts;

    @Autowired
    public OverheadProcessor(CashflowService cashflowService,
                             @Qualifier("FixedCosts") List<FixedCost> fixedCosts) {
        this.cashflowService = cashflowService;
        this.fixedCosts = fixedCosts;
    }

    @Override
    public void process(LocalDate date) {
        int dayOfMonth = date.getDayOfMonth();

        fixedCosts.stream()
                .filter(fc -> fc.getDaysOfMonth().contains(dayOfMonth))
                .forEach(fc -> {
                    logger.debug(String.format("Fixed cost of %s for %s", fc.getCost(), fc.getDescription()));
                    cashflowService.saveCashflow(new Cashflow(date, (-1)*fc.getCost()));
                });
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
