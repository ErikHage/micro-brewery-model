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

import java.util.Queue;

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
    private ProcessDefinition processDefinition;

    @Autowired
    public ModelController(InventoryService inventoryService,
                           ProcessDefinition processDefinition) {
        this.inventoryService = inventoryService;
        this.processDefinition = processDefinition;
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

        Queue<Processor> processorsForDay = processDefinition.getProcesses(date);
        processorsForDay.forEach(p -> p.process(date));
    }

}
