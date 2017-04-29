package com.tfr.microbrew.processor;

import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.dao.BatchDao;
import com.tfr.microbrew.service.InventoryService;
import com.tfr.microbrew.service.SalesService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * Created by Erik on 4/28/2017.
 */
@Component("ReportingProcessor")
public class ReportingProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BatchDao batchDao;
    private SalesService salesService;
    private InventoryService inventoryService;

    @Autowired
    public ReportingProcessor(BatchDao batchDao,
                              SalesService salesService,
                              InventoryService inventoryService) {
        this.batchDao = batchDao;
        this.salesService = salesService;
        this.inventoryService = inventoryService;
    }

    @Override
    public void process(LocalDate date) {
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
        sb.append(String.format("\tCompleted Sales  : %s\n", salesService.getSales(true)));
        sb.append(String.format("\tUnfulfilled Sales: %s\n", salesService.getSales(false)));
        sb.append("\tBy Volume\n");
//        salesByVolume.entrySet().forEach(e ->
//                sb.append(String.format("\t\t%-10s: %s \n", e.getKey(), e.getValue())));
//        sb.append("\tBy Product\n");
//        salesByProduct.entrySet().forEach(e ->
//                sb.append(String.format("\t\t%-30s: %s \n", e.getKey(), e.getValue())));
        logger.debug(sb.toString());
    }
}
