package com.tfr.microbrew.processor;

import com.google.common.collect.Sets;
import com.tfr.microbrew.config.Constants;
import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.model.InventoryItem;
import com.tfr.microbrew.service.BatchService;
import com.tfr.microbrew.service.InventoryService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tfr.microbrew.config.Constants.ACTIVE_PRODUCTS;
import static com.tfr.microbrew.config.Constants.BrewHouse.BATCH_SIZE;

/**
 *
 * Created by Erik on 4/28/2017.
 */
@Component("InventoryProcessor")
public class InventoryProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BatchService batchService;
    private InventoryService inventoryService;

    @Autowired
    public InventoryProcessor(BatchService batchService,
                              InventoryService inventoryService) {
        this.batchService = batchService;
        this.inventoryService = inventoryService;
    }

    @Override
    public void process(LocalDate date) {
        logger.debug("Increasing age of all in progress batches by 1 day");
        batchService.getAllInProgress().forEach(b -> b.setDaysInStep(b.getDaysInStep()+1));

        logger.debug("Checking Inventory Levels");

        checkBeerInventory();
        checkInventoryLevels();

        //TODO calculate costs of orders
    }

    private void checkBeerInventory() {
        //Check which batches need to be brewed and place them in TO_BREW state
        List<String> toBrew = ACTIVE_PRODUCTS.stream()
                .map(productName -> inventoryService.getItemByName(productName))
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

        toBrew.forEach(productName -> batchService.addBatch(productName));
    }

    private void checkInventoryLevels() {
        List<InventoryItem> itemsToReorder = inventoryService.getInventory()
                .stream()
                .filter(i -> ! i.getCategory().equals(Constants.InventoryCategory.BEER))
                .filter(i -> i.getQuantity() < i.getReorderThreshold())
                .collect(Collectors.toList());

        if(itemsToReorder.size() > 0) {
            logger.debug(String.format("Found %s items to be reordered", itemsToReorder.size()));
        } else {
            logger.debug("No items to reorder");
        }

        itemsToReorder.forEach(i -> {
            //TODO add ordering time delay for items
            i.setQuantity(i.getQuantity() + i.getReorderQuantity());
        });
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
