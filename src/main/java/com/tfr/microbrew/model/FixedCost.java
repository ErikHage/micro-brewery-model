package com.tfr.microbrew.model;

import java.util.Set;

/**
 *
 * Created by Erik Hage on 5/26/2017.
 */
public class FixedCost {

    private String description;
    private Double cost;

    private Set<Integer> daysOfMonth;

    public FixedCost(String description, Double cost, Set<Integer> daysOfMonth) {
        this.description = description;
        this.cost = cost;
        this.daysOfMonth = daysOfMonth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Set<Integer> getDaysOfMonth() {
        return daysOfMonth;
    }

    public void setDaysOfMonth(Set<Integer> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }
}
