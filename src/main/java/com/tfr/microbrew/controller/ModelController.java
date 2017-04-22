package com.tfr.microbrew.controller;

import com.google.common.collect.Sets;
import com.tfr.microbrew.compare.BatchPriorityComparator;
import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.model.Batch;
import com.tfr.microbrew.model.InitialParameters;
import com.tfr.microbrew.service.InventoryService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tfr.microbrew.config.Constants.*;
import static com.tfr.microbrew.config.Constants.BrewHouse.BREW_THRESHOLD;

/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
@Component("ModelController")
public class ModelController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private InventoryService inventoryService;
    private BatchPriorityComparator batchPriorityComparator;

    private final Queue<String> BATCHES_TO_BREW = new PriorityQueue<>(batchPriorityComparator);
    private final Set<Batch> IN_PROGRESS_BATCHES = Sets.newHashSet();

    @Autowired
    public ModelController(InventoryService inventoryService, BatchPriorityComparator batchPriorityComparator) {
        this.inventoryService = inventoryService;
        this.batchPriorityComparator = batchPriorityComparator;
    }

    public void runModel(InitialParameters initialParameters) {
        logger.debug("Started Model Execution");

        setInitialContext(initialParameters);

        LocalDate start = initialParameters.getStartDate();
        LocalDate end = initialParameters.getEndDate();

        for(LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            processDay(date);
        }

        logger.debug("Model execution completed");
    }

    private void setInitialContext(InitialParameters initialParameters) {
        logger.debug("Initializing starting state");
        initialParameters.getInitialInventory().forEach(inventoryService::addItem);
        logger.debug("Starting state initialized");
    }

    private void processDay(LocalDate date) {
        DayOfWeek dayOfWeek = DayOfWeek.getFromInt(date.getDayOfWeek());
        logger.debug(String.format("-------------------- Processing for %s %s --------------------",
                dayOfWeek.getName(), date));

        checkInventory();

        if(PROCESSING_DAYS.contains(dayOfWeek)) {
            logger.debug("Processing Day");
            processingDay();
        }

        if(BREW_DAYS.contains(dayOfWeek)) {
            logger.debug("Processing Day");
            brewDay();
        }

        if(BUSINESS_DAYS.contains(dayOfWeek)) {
            logger.debug("Processing Day");
            businessDay();
        }
    }

    private void checkInventory() {
        logger.debug("Checking Inventory Levels");
        List<String> toBrew = ACTIVE_PRODUCTS.stream()
                .filter(productName -> !BATCHES_TO_BREW.contains(productName))
                .filter(productName -> inventoryService.getCurrentQuantity(productName) < BREW_THRESHOLD)
                .collect(Collectors.toList());
        logger.debug(String.format("Adding these %s batches to the queue: %s", toBrew.size(), toBrew));
        BATCHES_TO_BREW.addAll(toBrew);

        //Check other inventory levels, place orders
        //calculate costs
    }

    private void processingDay() {
        //move from carbonation stage to storage
        //move from fermentation to carbonation
    }

    private void brewDay() {
        //if fermenter available, brew a batch from the queue and start fermentation
        //add to in progress batches
        //update inventory for the recipe ingredients
    }

    private void businessDay() {
        //calculate changes in inventory for sales
        //calculate revenues
        //note any unhappy customers
    }


}
