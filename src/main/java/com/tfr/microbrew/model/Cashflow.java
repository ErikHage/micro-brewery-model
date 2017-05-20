package com.tfr.microbrew.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tfr.microbrew.model.serialize.LocalDateJsonDeserializer;
import com.tfr.microbrew.model.serialize.LocalDateJsonSerializer;
import org.joda.time.LocalDate;

/**
 *
 * Created by Erik on 5/19/2017.
 */
public class Cashflow {

    @JsonDeserialize(using = LocalDateJsonDeserializer.class)
    @JsonSerialize(using = LocalDateJsonSerializer.class)
    private LocalDate date;

    private double amount;

    public Cashflow() {

    }

    public Cashflow(LocalDate date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
