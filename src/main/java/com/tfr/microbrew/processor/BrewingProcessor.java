package com.tfr.microbrew.processor;

import com.tfr.microbrew.compare.BrewPriorityComparator;
import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.config.Constants;
import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.model.Batch;
import com.tfr.microbrew.service.BatchService;
import com.tfr.microbrew.service.InventoryService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 *
 * Created by Erik on 4/28/2017.
 */
@Component("BrewingProcessor")
public class BrewingProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BatchService batchService;
    private InventoryService inventoryService;
    private BrewPriorityComparator brewPriorityComparator;

    @Autowired
    public BrewingProcessor(BatchService batchService,
                            InventoryService inventoryService,
                            BrewPriorityComparator brewPriorityComparator) {
        this.batchService = batchService;
        this.inventoryService = inventoryService;
        this.brewPriorityComparator = brewPriorityComparator;
    }

    @Override
    public void process(LocalDate date) {
        logger.debug("Brewing Day");

        if(batchService.getByStep(BrewStep.TO_BREW).size() > 0) {
            if (batchService.getByStep(BrewStep.FERMENT).size() < Constants.BrewHouse.FERMENTING_VESSELS) {
                Batch batchToBrew = getNextBatch();
                if(verifyIngredientsInStock(batchToBrew)) {
                    removeIngredientsFromInventory(batchToBrew);
                    brewBatch(batchToBrew);
                } else {
                    logger.debug(String.format("Cannot brew %s due to insufficient inventory", batchToBrew.getRecipe().getName()));
                    //TODO what to do when not in stock? reorder/add to reorder list? (should be taken care of in inventory check and reorder)
                }
            } else {
                logger.debug("No Space in fermenters to brew a new batch today");
            }
        } else {
            logger.debug("No batches to brew in queue today");
        }
    }

    @Override
    public Set<DayOfWeek> getDaysToProcess() {
        return Constants.BREW_DAYS;
    }

    @Override
    public String getName() {
        return "BrewingProcessor";
    }

    private Batch getNextBatch() {
        SortedSet<Batch> batchesToBrew = new TreeSet<>(brewPriorityComparator);
        batchesToBrew.addAll(batchService.getByStep(BrewStep.TO_BREW));
        return batchesToBrew.first();
    }

    private boolean verifyIngredientsInStock(Batch batch) {
        long num = batch.getRecipe().getIngredients().stream()
                .filter(i -> inventoryService.getCurrentQuantity(i.getName()) < i.getQuantity())
                .count();

        return num <= 0;
    }

    private void removeIngredientsFromInventory(Batch batchToBrew) {
        batchToBrew.getRecipe().getIngredients()
                .forEach(i -> inventoryService.updateQuantity(i.getName(), i.getQuantity()*(-1)));
    }

    private void brewBatch(Batch batch) {
        batch.setCurrentStep(BrewStep.FERMENT);
        batch.setDaysInStep(0);
        logger.debug(String.format("Batch of %s was brewed and is now fermenting",
                batch.getRecipe().getName()));
    }
}
