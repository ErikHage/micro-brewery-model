package com.tfr.microbrew.config;

import com.tfr.microbrew.processor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Configuration for Processor and ProcessDefinition beans
 *
 * Created by Erik on 5/1/2017.
 */
@Configuration
public class ProcessorConfig {

    @Bean(name = "DailyProcessors")
    public Queue<Processor> dailyProcessors(InventoryProcessor inventoryProcessor,
                                            TransferProcessor transferProcessor,
                                            BrewingProcessor brewingProcessor,
                                            SalesProcessor salesProcessor,
                                            OverheadProcessor overheadProcessor,
                                            ReportingProcessor reportingProcessor) {
        Queue<Processor> processors = new LinkedList<>();

        //enqueue in order of execution
        processors.add(inventoryProcessor);
        processors.add(transferProcessor);
        processors.add(brewingProcessor);
        processors.add(salesProcessor);
        processors.add(overheadProcessor);
        processors.add(reportingProcessor);

        return processors;
    }

}
