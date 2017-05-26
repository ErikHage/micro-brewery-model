package com.tfr.microbrew.model;

import com.tfr.microbrew.config.BrewStep;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by Erik Hage on 5/25/2017.
 */
public class ContextSummary {

    private String startDate;
    private String endDate;

    private double cashIn;
    private double cashOut;
    private double totalCashflow;

    private int numberOfDays;

    private Map<String, Integer> batches;

    private int numberOfBatches;

    private long potentialSales;
    private long fulfilledSales;
    private long unfulfilledSales;

    public ContextSummary() {
        this.cashIn = 0;
        this.cashOut = 0;
        this.totalCashflow = 0;
        this.numberOfDays = 0;
        this.batches = new HashMap<>();
        this.numberOfBatches = 0;
        this.potentialSales = 0;
        this.fulfilledSales = 0;
        this.unfulfilledSales = 0;
    }

    public void addContext(Context context) {
        double contextCashIn = context.getCashflows().stream()
                .filter(c -> c.getAmount() > 0)
                .mapToDouble(Cashflow::getAmount)
                .sum();

        double contextCashOut = context.getCashflows().stream()
                .filter(c -> c.getAmount() < 0)
                .mapToDouble(Cashflow::getAmount)
                .sum();

        double contextCashflow = contextCashIn + contextCashOut;

        cashIn = cashIn + contextCashIn;
        cashOut = cashOut + contextCashOut;
        totalCashflow = totalCashflow + contextCashflow;

        numberOfDays++;

        context.getBatches().stream()
                .filter(b -> b.getCurrentStep().equals(BrewStep.FERMENT))
                .filter(b -> b.getDaysInStep() == 0)
                .forEach(b -> {
                    String name = b.getRecipe().getName();
                    if(batches.containsKey(name)) {
                        batches.put(name, batches.get(name)+1);
                    } else {
                        batches.put(name, 1);
                    }
                });

        numberOfBatches = batches.entrySet().stream()
                .mapToInt(Map.Entry::getValue)
                .sum();

        long fulSales = context.getSales().stream()
                .filter(Sale::isFulfilled)
                .count();

        long unfulSales = context.getSales().stream()
                .filter(s -> !s.isFulfilled())
                .count();

        fulfilledSales = fulfilledSales + fulSales;
        unfulfilledSales = unfulfilledSales + unfulSales;
        potentialSales = potentialSales + fulSales + unfulSales;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getCashIn() {
        return cashIn;
    }

    public void setCashIn(double cashIn) {
        this.cashIn = cashIn;
    }

    public double getCashOut() {
        return cashOut;
    }

    public void setCashOut(double cashOut) {
        this.cashOut = cashOut;
    }

    public double getTotalCashflow() {
        return totalCashflow;
    }

    public void setTotalCashflow(double totalCashflow) {
        this.totalCashflow = totalCashflow;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Map<String, Integer> getBatches() {
        return batches;
    }

    public void setBatches(Map<String, Integer> batches) {
        this.batches = batches;
    }

    public int getNumberOfBatches() {
        return numberOfBatches;
    }

    public void setNumberOfBatches(int numberOfBatches) {
        this.numberOfBatches = numberOfBatches;
    }

    public long getPotentialSales() {
        return potentialSales;
    }

    public void setPotentialSales(long potentialSales) {
        this.potentialSales = potentialSales;
    }

    public long getFulfilledSales() {
        return fulfilledSales;
    }

    public void setFulfilledSales(long fulfilledSales) {
        this.fulfilledSales = fulfilledSales;
    }

    public long getUnfulfilledSales() {
        return unfulfilledSales;
    }

    public void setUnfulfilledSales(long unfulfilledSales) {
        this.unfulfilledSales = unfulfilledSales;
    }
}
