package com.tfr.microbrew.processor;

import com.tfr.microbrew.config.DayOfWeek;
import org.joda.time.LocalDate;

import java.util.Set;

/**
 *
 *
 * Created by Erik on 4/28/2017.
 */
public interface Processor {

    void process(LocalDate date);

    Set<DayOfWeek> getDaysToProcess();

    String getName();

}
