package com.tfr.microbrew.processor;

import com.tfr.microbrew.config.DayOfWeek;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 *
 *
 * Created by Erik on 4/29/2017.
 */
@Component("ProcessDefinition")
public class ProcessDefinition {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "DailyProcessors")
    private Queue<Processor> dailyProcessors;

    public ProcessDefinition() {
    }

    public Queue<Processor> getProcesses(LocalDate date) {
        Queue<Processor> processorsForDay;
        DayOfWeek dayOfWeek = DayOfWeek.getFromInt(date.getDayOfWeek());

        processorsForDay = dailyProcessors.stream()
                .filter(p -> p.getDaysToProcess().contains(dayOfWeek))
                .collect(Collectors.toCollection(LinkedList::new));

        logResult(processorsForDay);
        return processorsForDay;
    }

    private void logResult(Queue<Processor> processorsForDay) {
        StringBuilder sb = new StringBuilder("Processors: ");
        processorsForDay.forEach(p -> sb.append(String.format("%s, ", p.getName())));
        logger.debug(sb.toString());
    }

}
