package com.tfr.microbrew.processor;

import com.tfr.microbrew.dao.BatchDao;
import com.tfr.microbrew.helper.BatchHelper;
import com.tfr.microbrew.model.Batch;
import com.tfr.microbrew.model.InventoryItem;
import com.tfr.microbrew.service.InventoryService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tfr.microbrew.config.Constants.ACTIVE_PRODUCTS;

/**
 *
 * Created by Erik on 4/28/2017.
 */
@Component("InventoryProcessor")
public class InventoryProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BatchDao batchDao;
    private InventoryService inventoryService;

    @Autowired
    public InventoryProcessor(BatchDao batchDao,
                              InventoryService inventoryService) {
        this.batchDao = batchDao;
        this.inventoryService = inventoryService;
    }

    @Override
    public void process(LocalDate date) {
        logger.debug("Increasing age of all in progress batches by 1 day");
        batchDao.readAll().forEach(b -> b.setDaysInStep(b.getDaysInStep()+1));

        logger.debug("Checking Inventory Levels");

        //Check which batches need to be brewed and place them in TO_BREW state
        List<String> toBrew = ACTIVE_PRODUCTS.stream()
                .map(productName -> inventoryService.getItemByName(productName))
                .filter(Objects::nonNull)
                .filter(product -> batchDao.readByRecipe(product.getName()).size() == 0)
                .filter(product -> product.getQuantity() < product.getReorderThreshold())
                .map(InventoryItem::getName)
                .collect(Collectors.toList());

        logger.debug(String.format("Adding these %s batches to the queue: %s", toBrew.size(), toBrew));

        toBrew.forEach(productName -> {
            Batch batch = BatchHelper.getBatch(productName);
            batchDao.create(batch);
        });

        //TODO Check other inventory levels, place orders
        //TODO calculate costs of orders
    }
}
