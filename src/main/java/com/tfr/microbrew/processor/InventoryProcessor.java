package com.tfr.microbrew.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tfr.microbrew.config.Constants;
import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.model.Cashflow;
import com.tfr.microbrew.model.InventoryItem;
import com.tfr.microbrew.model.Recipe;
import com.tfr.microbrew.service.BatchService;
import com.tfr.microbrew.service.CashflowService;
import com.tfr.microbrew.service.InventoryService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tfr.microbrew.config.Constants.BrewHouse.BATCH_SIZE;
import static com.tfr.microbrew.config.Constants.RESTOCK_INVENTORY_DAYS;

/**
 *
 * Created by Erik on 4/28/2017.
 */
@Component("InventoryProcessor")
public class InventoryProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BatchService batchService;
    private final InventoryService inventoryService;
    private final CashflowService cashflowService;

    private static final Set<InventoryItem> itemsToReorder = Sets.newHashSet();

    private final List<Recipe> recipes;

    @Autowired
    public InventoryProcessor(BatchService batchService,
                              InventoryService inventoryService,
                              CashflowService cashflowService,
                              @Qualifier("RecipesList") List<Recipe> recipes) {
        this.batchService = batchService;
        this.inventoryService = inventoryService;
        this.cashflowService = cashflowService;
        this.recipes = recipes;
    }

    @Override
    public void process(LocalDate date) {
        logger.debug("Increasing age of all in progress batches by 1 day");
        batchService.getAllInProgress().forEach(b -> b.setDaysInStep(b.getDaysInStep()+1));

        logger.debug("Checking Inventory Levels");

        checkBeerInventory();
        checkInventoryLevels();

        if(RESTOCK_INVENTORY_DAYS.contains(DayOfWeek.getFromInt(date.getDayOfWeek()))) {
            restock(date);
        } else {
            logger.debug("Not a reorder day");
        }

        //TODO calculate costs of orders
    }

    private void checkBeerInventory() {
        //Check which batches need to be brewed and place them in TO_BREW state
        List<String> toBrew = recipes.stream()
                .map(r -> inventoryService.getItemByName(r.getName()))
                .filter(Objects::nonNull)
                .filter(product -> {
                    int inProgressBatches = batchService.getByRecipe(product.getName()).size();
                    double currentGallons = (BATCH_SIZE * inProgressBatches) + product.getQuantity();
                    return currentGallons < product.getReorderThreshold();
                })
                .filter(product -> product.getQuantity() < product.getReorderThreshold())
                .map(InventoryItem::getName)
                .collect(Collectors.toList());

        logger.debug(String.format("Adding these %s batches to the queue: %s", toBrew.size(), toBrew));

        toBrew.forEach(batchService::addBatch);
    }

    private void checkInventoryLevels() {
        itemsToReorder.addAll(inventoryService.getInventory()
                .stream()
                .filter(i -> ! i.getCategory().equals(Constants.InventoryCategory.BEER))
                .filter(i -> i.getQuantity() < i.getReorderThreshold())
                .collect(Collectors.toList()));
    }

    private void restock(LocalDate date) {
        logger.debug(String.format("%s items to be reordered", itemsToReorder.size()));
        itemsToReorder.forEach(i -> {
            double cost = i.getReorderQuantity() * i.getUnitPrice();
            i.setQuantity(i.getQuantity() + i.getReorderQuantity());
            cashflowService.saveCashflow(new Cashflow(date, (-1)*cost, "Inventory"));
        });
        itemsToReorder.clear();
    }

    @Override
    public Set<DayOfWeek> getDaysToProcess() {
        return Sets.newHashSet(DayOfWeek.values());
    }

    @Override
    public String getName() {
        return "InventoryProcessor";
    }
}
