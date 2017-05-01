package com.tfr.microbrew.processor;

import com.tfr.microbrew.config.BeverageVolume;
import com.tfr.microbrew.config.Constants;
import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.config.SalesConfig;
import com.tfr.microbrew.model.Sale;
import com.tfr.microbrew.service.InventoryService;
import com.tfr.microbrew.service.SalesService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *
 * Created by Erik on 4/28/2017.
 */
@Component("SalesProcessor")
public class SalesProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SalesService salesService;
    private final InventoryService inventoryService;

    @Autowired
    public SalesProcessor(SalesService salesService, InventoryService inventoryService) {
        this.salesService = salesService;
        this.inventoryService = inventoryService;
    }

    @Override
    public void process(LocalDate date) {
        DayOfWeek dayOfWeek = DayOfWeek.getFromInt(date.getDayOfWeek());
        logger.debug("Business Day : " + dayOfWeek.getName());

        AtomicInteger fulfilledSales = new AtomicInteger(0);
        AtomicInteger unfulfilledSales = new AtomicInteger(0);

        //generate sales
        int numberOfSales = getProjectedNumberOfSales(dayOfWeek);
        List<Sale> sales = salesService.generateSales(numberOfSales);

        //attempt to fulfill Sales
        sales.forEach(s -> {
            if(inventoryService.getCurrentQuantity(s.getProductName()) > s.getBeverageProduct().getVolume()) {
                inventoryService.updateQuantity(s.getProductName(),
                        s.getBeverageProduct().getVolume()*(-1));
                s.setFulfilled(true);
                fulfilledSales.getAndIncrement();
            } else {
                s.setNotFulfilledReason("Sale not fulfilled because of insufficient inventory");
                unfulfilledSales.getAndIncrement();
            }
            salesService.performSale(s);
        });

        logger.debug(String.format("Sales Today: %s [%s fulfilled | %s unfulfilled]",
                numberOfSales, fulfilledSales.get(), unfulfilledSales.get()));
        //TODO calculate changes in inventory for sales
        //TODO calculate revenues
        //TODO note any unhappy customers
    }

    @Override
    public Set<DayOfWeek> getDaysToProcess() {
        return Constants.BUSINESS_DAYS;
    }

    @Override
    public String getName() {
        return "SalesProcessor";
    }

    private int getProjectedNumberOfSales(DayOfWeek dayOfWeek) {
        int num = ThreadLocalRandom.current().nextInt(SalesConfig.MIN_SALES, SalesConfig.MAX_SALES+1);
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            num = (int) (num * SalesConfig.WEEKEND_FACTOR);
        }
        return num;
    }


}
