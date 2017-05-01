package com.tfr.microbrew.processor;

import com.tfr.microbrew.config.DayOfWeek;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 *
 * Created by Erik on 4/29/2017.
 */
@Component("ProcessDefinition")
public class ProcessDefinition {

//    private final InventoryProcessor inventoryProcessor;
//    private final BrewingProcessor brewingProcessor;
//    private final TransferProcessor transferProcessor;
//    private final SalesProcessor salesProcessor;
//    private final ReportingProcessor reportingProcessor;

    private final List<Processor> processors;

    @Autowired
    public ProcessDefinition(InventoryProcessor inventoryProcessor,
                             BrewingProcessor brewingProcessor,
                             TransferProcessor transferProcessor,
                             SalesProcessor salesProcessor,
                             ReportingProcessor reportingProcessor) {
//        this.inventoryProcessor = inventoryProcessor;
//        this.brewingProcessor = brewingProcessor;
//        this.transferProcessor = transferProcessor;
//        this.salesProcessor = salesProcessor;
//        this.reportingProcessor = reportingProcessor;

        processors = new ArrayList<>();
        processors.add(inventoryProcessor);
        processors.add(transferProcessor);
        processors.add(brewingProcessor);
        processors.add(salesProcessor);
        processors.add(reportingProcessor);
    }

    public Queue<Processor> getProcesses(LocalDate date) {
        Queue<Processor> processorsForDay = new LinkedList<>();
        DayOfWeek dayOfWeek = DayOfWeek.getFromInt(date.getDayOfWeek());

        processors.forEach(p -> {
            if(p.getDaysToProcess().contains(dayOfWeek)) {
                processorsForDay.add(p);
            }
        });

        return processorsForDay;
    }

}
