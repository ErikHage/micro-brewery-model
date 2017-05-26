package com.tfr.microbrew.model;

import org.joda.time.LocalDate;

/**
 *
 * Created by Erik Hage on 5/25/2017.
 */
public class ContextSummary {

    private LocalDate start;
    private LocalDate end;

    private double cashIn;
    private double cashOut;
    private double totalCashflow;

    public ContextSummary() {
        this.cashIn = 0;
        this.cashOut = 0;
        this.totalCashflow = 0;
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
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
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
}
