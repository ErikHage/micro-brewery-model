package com.tfr.microbrew.controller;

import com.google.common.collect.Sets;
import com.tfr.microbrew.compare.BatchPriorityComparator;
import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.dao.BatchDao;
import com.tfr.microbrew.helper.BatchHelper;
import com.tfr.microbrew.model.Batch;
import com.tfr.microbrew.model.InitialParameters;
import com.tfr.microbrew.model.InventoryItem;
import com.tfr.microbrew.service.InventoryService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
    private BatchDao batchDao;
    private BatchPriorityComparator batchPriorityComparator;

    private final Queue<String> BATCHES_TO_BREW = new PriorityQueue<>(batchPriorityComparator);
    private final Set<Batch> IN_PROGRESS_BATCHES = Sets.newHashSet();

    @Autowired
    public ModelController(InventoryService inventoryService, BatchDao batchDao,
                           BatchPriorityComparator batchPriorityComparator) {
        this.inventoryService = inventoryService;
        this.batchDao = batchDao;
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
        initialParameters.getInitialInventory().forEach(inventoryService::updateItem);
        logger.debug("Starting state initialized");
    }

    private void processDay(LocalDate date) {
        DayOfWeek dayOfWeek = DayOfWeek.getFromInt(date.getDayOfWeek());
        logger.debug(String.format("-------------------- Processing for %s %s --------------------",
                dayOfWeek.getName(), date));

        everydayActions();

        if(PROCESSING_DAYS.contains(dayOfWeek)) {
            logger.debug("Processing Day");
            processingDay();
        }

        if(BREW_DAYS.contains(dayOfWeek)) {
            logger.debug("Brewing Day");
            brewDay();
        }

        if(BUSINESS_DAYS.contains(dayOfWeek)) {
            logger.debug("Business Day");
            businessDay();
        }

        printDayReport(date);
    }

    private void everydayActions() {
        checkInventory();

        //count the days in each step
        batchDao.readAll().forEach(b -> b.setDaysInStep(b.getDaysInStep()+1));
    }

    private void checkInventory() {
        logger.debug("Checking Inventory Levels");
        List<String> toBrew = ACTIVE_PRODUCTS.stream()
                .map(productName -> inventoryService.getItemByName(productName))
                .filter(Objects::nonNull)
                .filter(product -> !BATCHES_TO_BREW.contains(product.getName()))
                .filter(product -> product.getQuantity() < product.getReorderThreshold())
                .filter(product -> batchDao.readByRecipe(product.getName()).size() == 0)
                .map(InventoryItem::getName)
                .collect(Collectors.toList());
        logger.debug(String.format("Adding these %s batches to the queue: %s", toBrew.size(), toBrew));
        BATCHES_TO_BREW.addAll(toBrew);

        //TODO Check other inventory levels, place orders
        //TODO calculate costs
    }

    private void processingDay() {
        //TODO move from carbonation stage to storage
        //TODO move from fermentation to carbonation
    }

    private void brewDay() {
        //if fermenter available, brew a batch from the queue and start fermentation
        if(BATCHES_TO_BREW.size() > 0) {
            if (batchDao.readByStep(BrewStep.FERMENT).size() < 8) {
                String nextBatch = BATCHES_TO_BREW.poll();
                Batch batch = BatchHelper.getBatch(nextBatch);
                batch.setCurrentStep(BrewStep.FERMENT);
                batch.setDaysInStep(0);
                batchDao.create(batch);
                logger.debug(String.format("Batch of %s was brewed and is now fermenting",
                        batch.getRecipe().getName()));
            } else {
                logger.debug("No Space in fermenters to brew a new batch today");
            }
        } else {
            logger.debug("No batches to brew in queue today");
        }

        //TODO update inventory for the recipe ingredients
    }

    private void businessDay() {
        //calculate changes in inventory for sales
        //calculate revenues
        //note any unhappy customers
    }

    private void printDayReport(LocalDate date) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("----------End of Day Report: %s ----------\n", date));
        sb.append("Batch Status\n");
        sb.append("\tFermenting Batches\n");
        batchDao.readByStep(BrewStep.FERMENT).forEach(batch ->
                sb.append(String.format("\t--%s : %s days\n", batch.getRecipe().getName(), batch.getDaysInStep())));
        sb.append("\tCarbonating Batches\n");
        batchDao.readByStep(BrewStep.CARBONATE).forEach(batch ->
                sb.append(String.format("\t--%s : %s days\n", batch.getRecipe().getName(), batch.getDaysInStep())));
        sb.append("\n");
        sb.append("");

        logger.debug(sb.toString());

    }

}
