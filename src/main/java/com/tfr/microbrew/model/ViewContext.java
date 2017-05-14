package com.tfr.microbrew.model;

import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * Created by Erik Hage on 5/13/2017.
 */
public class ViewContext {

    private final Context context;

    private final Map<String, Long> batchesByProduct;
    private final Map<String, Long> batchesByStep;

    private final int totalBatches;

    private final Map<String, Long> salesByProduct;
    private final Map<String, Long> fulfilledSalesByProduct;
    private final Map<String, Long> unfulfilledSalesByProduct;

    private final int totalSales;
    private final int totalFulfilledSales;
    private final int totalUnfulfilledSales;



    public ViewContext(Context context) {
        this.context = context;

        this.batchesByProduct = context.getBatches().stream()
                .collect(Collectors.groupingBy(e -> e.getRecipe().getName(),
                        Collectors.counting()));

        this.batchesByStep = context.getBatches().stream()
                .collect(Collectors.groupingBy(e -> e.getCurrentStep().getValue(),
                        Collectors.counting()));

        this.totalBatches = context.getBatches().size();

        this.salesByProduct = context.getSales().stream()
                .collect(Collectors.groupingBy(Sale::getProductName, Collectors.counting()));
        this.totalSales = context.getSales().size();

        this.fulfilledSalesByProduct = context.getSales().stream()
                .filter(Sale::isFulfilled)
                .collect(Collectors.groupingBy(Sale::getProductName, Collectors.counting()));
        this.totalFulfilledSales = context.getSales().stream()
                .filter(Sale::isFulfilled)
                .collect(Collectors.toList())
                .size();

        this.unfulfilledSalesByProduct = context.getSales().stream()
                .filter(s -> !s.isFulfilled())
                .collect(Collectors.groupingBy(Sale::getProductName, Collectors.counting()));
        this.totalUnfulfilledSales = context.getSales().stream()
                .filter(s -> !s.isFulfilled())
                .collect(Collectors.toList())
                .size();
    }

    public Context getContext() {
        return context;
    }

    public Map<String, Long> getSalesByProduct() {
        return salesByProduct;
    }

    public Map<String, Long> getFulfilledSalesByProduct() {
        return fulfilledSalesByProduct;
    }

    public Map<String, Long> getUnfulfilledSalesByProduct() {
        return unfulfilledSalesByProduct;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public int getTotalFulfilledSales() {
        return totalFulfilledSales;
    }

    public int getTotalUnfulfilledSales() {
        return totalUnfulfilledSales;
    }

    public Map<String, Long> getBatchesByProduct() {
        return batchesByProduct;
    }

    public int getTotalBatches() {
        return totalBatches;
    }

    public Map<String, Long> getBatchesByStep() {
        return batchesByStep;
    }
}
