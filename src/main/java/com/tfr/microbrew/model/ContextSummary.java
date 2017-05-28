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

    private Map<String, Double> cashflowsByDescription;
    private Map<String, Double> cashflowsByProduct;
    private Map<Integer, Double> cashflowsByMonth;

    private int numberOfDays;

    private Map<String, Integer> batches;

    private int numberOfBatches;

    private long potentialSales;
    private long fulfilledSales;
    private long unfulfilledSales;

    private Map<String, Integer> salesByProduct;
    private Map<String, Double> volumeByProduct;
    private Map<String, Integer> salesByVolume;

    public ContextSummary() {
        this.cashIn = 0;
        this.cashOut = 0;
        this.totalCashflow = 0;
        this.cashflowsByDescription = new HashMap<>();
        this.cashflowsByProduct = new HashMap<>();
        this.cashflowsByMonth = new HashMap<>();
        this.numberOfDays = 0;
        this.batches = new HashMap<>();
        this.numberOfBatches = 0;
        this.potentialSales = 0;
        this.fulfilledSales = 0;
        this.unfulfilledSales = 0;
        this.salesByProduct = new HashMap<>();
        this.volumeByProduct = new HashMap<>();
        this.salesByVolume = new HashMap<>();
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

        context.getCashflows().forEach(c -> {
            String description = c.getDescription();
            cashflowsByDescription.putIfAbsent(description, 0.0);
            cashflowsByDescription.put(description,
                    cashflowsByDescription.get(description) + c.getAmount());
        });

        context.getCashflows().forEach(c -> {
            int month = c.getDate().getMonthOfYear();
            cashflowsByMonth.putIfAbsent(month, 0.0);
            cashflowsByMonth.put(month, cashflowsByMonth.get(month) + c.getAmount());
        });

        context.getCashflows().stream()
                .filter(c -> c.getDescription().equals("Beer Sale"))
                .forEach(c -> {
                    cashflowsByProduct.putIfAbsent(c.getNote(), 0.0);
                    cashflowsByProduct.put(c.getNote(),
                            cashflowsByProduct.get(c.getNote()) + c.getAmount());
                });

        numberOfDays++;

        context.getBatches().stream()
                .filter(b -> b.getCurrentStep().equals(BrewStep.FERMENT))
                .filter(b -> b.getDaysInStep() == 0)
                .forEach(b -> {
                    String name = b.getRecipe().getName();
                    batches.putIfAbsent(name, 0);
                    batches.put(name, batches.get(name)+1);
                });

        context.getSales().stream()
                .filter(Sale::isFulfilled)
                .forEach(s -> {
                    salesByProduct.putIfAbsent(s.getProductName(), 0);
                    salesByProduct.put(s.getProductName(), salesByProduct.get(s.getProductName())+1);
                });

        context.getSales().stream()
                .filter(Sale::isFulfilled)
                .forEach(s -> {
                    volumeByProduct.putIfAbsent(s.getProductName(), 0.0);
                    volumeByProduct.put(s.getProductName(),
                            volumeByProduct.get(s.getProductName())+s.getBeverageProduct().getVolume());
                });

        context.getSales().stream()
                .filter(Sale::isFulfilled)
                .forEach(s -> {
                    String beverageVolume = s.getBeverageProduct().getBeverageVolume().name();
                    salesByVolume.putIfAbsent(beverageVolume, 0);
                    salesByVolume.put(beverageVolume,
                            salesByVolume.get(beverageVolume)+1);
                });

        numberOfBatches = batches.entrySet().stream()
                .mapToInt(Map.Entry::getValue)
                .sum();

        long fulSales = context.getSales().stream()
                .filter(Sale::isFulfilled)
                .count();

        long unfulSales = context.getSales().size() - fulSales;

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

    public Map<String, Integer> getSalesByProduct() {
        return salesByProduct;
    }

    public void setSalesByProduct(Map<String, Integer> salesByProduct) {
        this.salesByProduct = salesByProduct;
    }

    public Map<String, Double> getVolumeByProduct() {
        return volumeByProduct;
    }

    public void setVolumeByProduct(Map<String, Double> volumeByProduct) {
        this.volumeByProduct = volumeByProduct;
    }

    public Map<String, Integer> getSalesByVolume() {
        return salesByVolume;
    }

    public void setSalesByVolume(Map<String, Integer> salesByVolume) {
        this.salesByVolume = salesByVolume;
    }

    public Map<String, Double> getCashflowsByDescription() {
        return cashflowsByDescription;
    }

    public void setCashflowsByDescription(Map<String, Double> cashflowsByDescription) {
        this.cashflowsByDescription = cashflowsByDescription;
    }

    public Map<String, Double> getCashflowsByProduct() {
        return cashflowsByProduct;
    }

    public void setCashflowsByProduct(Map<String, Double> cashflowsByProduct) {
        this.cashflowsByProduct = cashflowsByProduct;
    }

    public Map<Integer, Double> getCashflowsByMonth() {
        return cashflowsByMonth;
    }

    public void setCashflowsByMonth(Map<Integer, Double> cashflowsByMonth) {
        this.cashflowsByMonth = cashflowsByMonth;
    }
}
