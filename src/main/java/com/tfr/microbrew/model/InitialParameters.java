package com.tfr.microbrew.model;

import org.joda.time.LocalDate;

import java.util.Map;
import java.util.Set;

/**
 * Starting parameter for model
 *
 * Created by Erik on 4/22/2017.
 */
public class InitialParameters {

    private Map<String, Double> initialInventory;

    private LocalDate startDate;
    private LocalDate endDate;

    public InitialParameters() {

    }

    public Map<String, Double> getInitialInventory() {
        return initialInventory;
    }

    public void setInitialInventory(Map<String, Double> initialInventory) {
        this.initialInventory = initialInventory;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
