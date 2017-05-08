package com.tfr.microbrew.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.config.ContextId;
import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.model.Batch;
import com.tfr.microbrew.model.Context;
import com.tfr.microbrew.model.InventoryItem;
import com.tfr.microbrew.model.Sale;
import com.tfr.microbrew.service.BatchService;
import com.tfr.microbrew.service.InventoryService;
import com.tfr.microbrew.service.SalesService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 *
 * Created by Erik on 4/28/2017.
 */
@Component("ReportingProcessor")
public class ReportingProcessor implements Processor {

    private static final String CONTEXT_DIRECTORY = "logs/context/";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ObjectMapper objectMapper = new ObjectMapper();

    private BatchService batchService;
    private SalesService salesService;
    private InventoryService inventoryService;
    private ContextId contextId;

    @Autowired
    public ReportingProcessor(BatchService batchService,
                              SalesService salesService,
                              InventoryService inventoryService,
                              ContextId contextId) {
        this.batchService = batchService;
        this.salesService = salesService;
        this.inventoryService = inventoryService;
        this.contextId = contextId;
    }

    @Override
    public void process(LocalDate date) {

        saveContext(date);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("----------End of Day Report: %s ----------\n", date));
        sb.append("Batch Status\n");
        sb.append("\tBatches To Brew\n");
        batchService.getByStep(BrewStep.TO_BREW).forEach(batch ->
                sb.append(String.format("\t\t%s : %s days\n", batch.getRecipe().getName(), batch.getDaysInStep())));
        sb.append("\tFermenting Batches\n");
        batchService.getByStep(BrewStep.FERMENT).forEach(batch ->
                sb.append(String.format("\t\t%s : %s days\n", batch.getRecipe().getName(), batch.getDaysInStep())));
        sb.append("\tCarbonating Batches\n");
        batchService.getByStep(BrewStep.CARBONATE).forEach(batch ->
                sb.append(String.format("\t\t%s : %s days\n", batch.getRecipe().getName(), batch.getDaysInStep())));
        sb.append("Inventory Status\n");
        inventoryService.getInventory().forEach(i ->
                sb.append(String.format("\t%-10s %s: %s\n", i.getCategory(), i.getName(), i.getQuantity())));
        sb.append("Sales Report\n");
        sb.append(String.format("\tCompleted Sales  : %s\n", salesService.getSales(true)));
        sb.append(String.format("\tUnfulfilled Sales: %s\n", salesService.getSales(false)));
//        sb.append("\tBy Volume\n");
//        salesByVolume.entrySet().forEach(e ->
//                sb.append(String.format("\t\t%-10s: %s \n", e.getKey(), e.getValue())));
//        sb.append("\tBy Product\n");
//        salesByProduct.entrySet().forEach(e ->
//                sb.append(String.format("\t\t%-30s: %s \n", e.getKey(), e.getValue())));

        sb.append("\tTotal Missed Sales by Product\n");
        salesService.getUnfulfilledSalesByProduct().entrySet().forEach(e ->
                sb.append(String.format("\t\t %s: %s \n", e.getKey(), e.getValue())));

        logger.debug(sb.toString());
    }

    private void saveContext(LocalDate date) {
        List<InventoryItem> inventoryItems = new ArrayList<>(inventoryService.getInventory());
        List<Batch> batches = new ArrayList<>(batchService.getAllInProgress());
        List<Sale> sales = salesService.getSalesByDate(date);

        Context context = new Context(date, inventoryItems, batches, sales);

        checkContextDirectory();

        try {
            String json = objectMapper.writeValueAsString(context);
            writeFile(date, json);
        } catch (Exception e) {
            logger.error("Error saving context to file", e);
        }
    }

    private void writeFile(LocalDate date, String json) throws IOException {
        String filename = date.toString() + ".json";
        File file = new File(CONTEXT_DIRECTORY + "/" + contextId.getContextId() + "/" + filename);

        try(FileWriter fw = new FileWriter(file.getAbsoluteFile())) {
            try(BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(json);
                logger.debug("Wrote file: " + CONTEXT_DIRECTORY + "/" + contextId.getContextId() + "/" + filename);
            }
        }
    }

    private void checkContextDirectory() {
        File directory = new File(CONTEXT_DIRECTORY + "/" + contextId.getContextId());
        if(!directory.exists()) {
            if(!directory.mkdir()) {
                logger.error("Failed to mkdir for " + CONTEXT_DIRECTORY + "/" + contextId.getContextId());
            }
        }
    }

    @Override
    public Set<DayOfWeek> getDaysToProcess() {
        return Sets.newHashSet(DayOfWeek.values());
    }

    @Override
    public String getName() {
        return "ReportingProcessor";
    }
}
