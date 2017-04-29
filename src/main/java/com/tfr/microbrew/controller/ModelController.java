package com.tfr.microbrew.controller;

import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.model.InitialParameters;
import com.tfr.microbrew.processor.*;
import com.tfr.microbrew.service.InventoryService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.tfr.microbrew.config.Constants.*;

/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
@Component("ModelController")
public class ModelController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private InventoryService inventoryService;

    private SalesProcessor salesProcessor;
    private BrewingProcessor brewingProcessor;
    private TransferProcessor transferProcessor;
    private ReportingProcessor reportingProcessor;
    private InventoryProcessor inventoryProcessor;

    @Autowired
    public ModelController(SalesProcessor salesProcessor,
                           BrewingProcessor brewingProcessor,
                           TransferProcessor transferProcessor,
                           ReportingProcessor reportingProcessor,
                           InventoryProcessor inventoryProcessor,
                           InventoryService inventoryService) {
        this.salesProcessor = salesProcessor;
        this.brewingProcessor = brewingProcessor;
        this.transferProcessor = transferProcessor;
        this.reportingProcessor = reportingProcessor;
        this.inventoryProcessor = inventoryProcessor;
        this.inventoryService = inventoryService;
    }

    public void runModel(InitialParameters initialParameters) {
        logger.debug("Started Model Execution");

        setInitialContext(initialParameters);

        LocalDate start = initialParameters.getStartDate();
        LocalDate end = initialParameters.getEndDate();

        //FIXME this needs to change later - when I want day by day reports in a UI
        for(LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            processDay(date);
        }

        logger.debug("Model execution completed");
    }

    private void setInitialContext(InitialParameters initialParameters) {
        logger.debug("Initializing starting state");
        initialParameters.getInitialInventory().forEach(inventoryService::updateItem);
        logger.debug("Starting state initialized");
    }

    private void processDay(LocalDate date) {
        DayOfWeek dayOfWeek = DayOfWeek.getFromInt(date.getDayOfWeek());
        logger.debug(String.format("-------------------- Processing for %s %s --------------------",
                dayOfWeek.getName(), date));

        inventoryProcessor.process(date);

        if(PROCESSING_DAYS.contains(dayOfWeek)) {
            transferProcessor.process(date);
        }
        if(BREW_DAYS.contains(dayOfWeek)) {
            brewingProcessor.process(date);
        }
        if(BUSINESS_DAYS.contains(dayOfWeek)) {
            salesProcessor.process(date);
        }

        reportingProcessor.process(date);
    }

}
