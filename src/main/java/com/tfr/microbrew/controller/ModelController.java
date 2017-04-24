package com.tfr.microbrew.controller;

import com.tfr.microbrew.compare.BrewPriorityComparator;
import com.tfr.microbrew.compare.CarbonationPriorityComparator;
import com.tfr.microbrew.config.BeverageVolume;
import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.config.SalesConfig;
import com.tfr.microbrew.dao.BatchDao;
import com.tfr.microbrew.helper.BatchHelper;
import com.tfr.microbrew.model.Batch;
import com.tfr.microbrew.model.InitialParameters;
import com.tfr.microbrew.model.InventoryItem;
import com.tfr.microbrew.model.Sale;
import com.tfr.microbrew.service.InventoryService;
import com.tfr.microbrew.service.SalesService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.tfr.microbrew.config.Constants.*;
import static com.tfr.microbrew.config.Constants.BrewHouse.CARBONATION_VESSELS;

/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
@Component("ModelController")
public class ModelController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BatchDao batchDao;
    private SalesService salesService;
    private InventoryService inventoryService;
    private BrewPriorityComparator brewPriorityComparator;
    private CarbonationPriorityComparator carbonationPriorityComparator;

    private final Supplier<SortedSet<Batch>> sortedSetSupplierCarbonation =
            () -> new TreeSet<>(carbonationPriorityComparator);

    private final AtomicInteger fulfilledSales = new AtomicInteger();
    private final AtomicInteger unfulfilledSales = new AtomicInteger();
    private final Map<BeverageVolume, Integer> salesByVolume = new HashMap<>();
    private final Map<String, Integer> salesbyProduct = new HashMap<>();

    @Autowired
    public ModelController(BatchDao batchDao,
                           SalesService salesService,
                           InventoryService inventoryService,
                           BrewPriorityComparator brewPriorityComparator,
                           CarbonationPriorityComparator carbonationPriorityComparator) {
        this.batchDao = batchDao;
        this.salesService = salesService;
        this.inventoryService = inventoryService;
        this.brewPriorityComparator = brewPriorityComparator;
        this.carbonationPriorityComparator = carbonationPriorityComparator;
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
        checkInventory();

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
            businessDay(dayOfWeek);
        }

        printDayReport(date);
    }

    private void everydayActions() {
        //increment the days in each step
        batchDao.readAll().forEach(b -> b.setDaysInStep(b.getDaysInStep()+1));
    }

    private void checkInventory() {
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





    private void processingDay() {
        //mark all completed carbonation as ready for storage
        batchDao.readByStep(BrewStep.CARBONATE).stream()
                .filter(b -> b.getDaysInStep() >= b.getRecipe().getCarbonationDays())
                .forEach(batch -> {
                    batch.setCurrentStep(BrewStep.PACKAGE);
                    batch.setDaysInStep(0);
                    logger.debug(String.format("Batch ready for packaging: %s", batch));
                });

        //move completed batches to inventory
        batchDao.readByStep(BrewStep.PACKAGE).forEach(batch -> {
            inventoryService.updateQuantity(batch.getRecipe().getName(), batch.getRecipe().getVolume());
            batchDao.delete(batch);
            logger.debug(String.format("Batch completed and moved to inventory: %s", batch));
        });

        //move all completed fermenting batches to carbonation vessels (if available)
        SortedSet<Batch> batchesToCarbonate = batchDao.readByStep(BrewStep.FERMENT).stream()
                .filter(b -> b.getDaysInStep() >= b.getRecipe().getFermentationDays())
                .collect(Collectors.toCollection(sortedSetSupplierCarbonation));

        batchesToCarbonate.forEach(batch -> {
            if(batchDao.readByStep(BrewStep.CARBONATE).size() < CARBONATION_VESSELS) {
                batch.setCurrentStep(BrewStep.CARBONATE);
                batch.setDaysInStep(0);
                logger.debug(String.format("Batch moved to carbonation vessel: %s", batch));
            } else {
                logger.debug(String.format("No available carbonation vessel for batch: %s", batch));
            }
        });
    }

    private void brewDay() {
        //if fermenter available, brew a batch from the TO_BREW batches and start fermentation
        if(batchDao.readByStep(BrewStep.TO_BREW).size() > 0) {
            if (batchDao.readByStep(BrewStep.FERMENT).size() < BrewHouse.FERMENTERS) {
                //sort by brewPriority
                SortedSet<Batch> batchesToBrew = new TreeSet<>(brewPriorityComparator);
                batchesToBrew.addAll(batchDao.readByStep(BrewStep.TO_BREW));
                Batch batchToBrew = batchesToBrew.first();

                //update progress of batch
                batchToBrew.setCurrentStep(BrewStep.FERMENT);
                batchToBrew.setDaysInStep(0);

                logger.debug(String.format("Batch of %s was brewed and is now fermenting",
                        batchToBrew.getRecipe().getName()));
            } else {
                logger.debug("No Space in fermenters to brew a new batch today");
            }
        } else {
            logger.debug("No batches to brew in queue today");
        }

        //TODO update inventory for the recipe ingredients
    }

    private void businessDay(DayOfWeek dayOfWeek) {
        //generate sales
        int numberOfSales = getProjectedNuberOfSales(dayOfWeek);
        List<Sale> sales = salesService.generateSales(numberOfSales);

        //attempt to fulfill Sales
        sales.forEach(s -> {
            if(inventoryService.getCurrentQuantity(s.getProductName()) > s.getBeverageProduct().getVolume()) {
                inventoryService.updateQuantity(s.getProductName(), s.getBeverageProduct().getVolume()*(-1));
                s.setFulfilled(true);
                fulfilledSales.incrementAndGet();

                salesByVolume.putIfAbsent(s.getBeverageProduct().getBeverageVolume(), 0);
                salesByVolume.put(s.getBeverageProduct().getBeverageVolume(), salesByVolume.get(s.getBeverageProduct().getBeverageVolume()) + 1);

                salesbyProduct.putIfAbsent(s.getProductName(), 0);
                salesbyProduct.put(s.getProductName(), salesbyProduct.get(s.getProductName())+1);
            } else {
                s.setNotFulfilledReason("Sale not fulfilled because of insufficient inventory");
                unfulfilledSales.incrementAndGet();
            }
            salesService.saveSale(s);
        });


        //TODO calculate changes in inventory for sales
        //TODO calculate revenues
        //TODO note any unhappy customers
    }

    private int getProjectedNuberOfSales(DayOfWeek dayOfWeek) {
        int num = ThreadLocalRandom.current().nextInt(SalesConfig.MIN_SALES, SalesConfig.MAX_SALES+1);
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            num = (int) (num * SalesConfig.WEEKEND_FACTOR);
        }
        return num;
    }

    private void printDayReport(LocalDate date) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("----------End of Day Report: %s ----------\n", date));
        sb.append("Batch Status\n");
        sb.append("\tBatches To Brew\n");
        batchDao.readByStep(BrewStep.TO_BREW).forEach(batch ->
                sb.append(String.format("\t\t%s : %s days\n", batch.getRecipe().getName(), batch.getDaysInStep())));
        sb.append("\tFermenting Batches\n");
        batchDao.readByStep(BrewStep.FERMENT).forEach(batch ->
                sb.append(String.format("\t\t%s : %s days\n", batch.getRecipe().getName(), batch.getDaysInStep())));
        sb.append("\tCarbonating Batches\n");
        batchDao.readByStep(BrewStep.CARBONATE).forEach(batch ->
                sb.append(String.format("\t\t%s : %s days\n", batch.getRecipe().getName(), batch.getDaysInStep())));
        sb.append("Inventory Status\n");
        inventoryService.getInventory().forEach(i ->
                sb.append(String.format("\t%-10s %s: %s\n", i.getCategory(), i.getName(), i.getQuantity())));
        sb.append("Sales Report\n");
        sb.append(String.format("\tCompleted Sales  : %s\n", fulfilledSales.get()));
        sb.append(String.format("\tUnfulfilled Sales: %s\n", unfulfilledSales.get()));
        sb.append("\tBy Volume\n");
        salesByVolume.entrySet().forEach(e ->
                sb.append(String.format("\t\t%-10s: %s \n", e.getKey(), e.getValue())));
        sb.append("\tBy Product\n");
        salesbyProduct.entrySet().forEach(e ->
                sb.append(String.format("\t\t%-30s: %s \n", e.getKey(), e.getValue())));
        logger.debug(sb.toString());
    }

}
