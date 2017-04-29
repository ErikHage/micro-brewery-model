package com.tfr.microbrew.processor;

import org.joda.time.LocalDate;

/**
 *
 *
 * Created by Erik on 4/28/2017.
 */
public interface Processor {

    void process(LocalDate date);

}
