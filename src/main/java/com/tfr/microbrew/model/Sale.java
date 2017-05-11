package com.tfr.microbrew.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tfr.microbrew.model.serialize.LocalDateJsonDeserializer;
import com.tfr.microbrew.model.serialize.LocalDateJsonSerializer;
import org.joda.time.LocalDate;

/**
 *
 *
 * Created by Erik on 4/24/2017.
 */
public class Sale {

    @JsonDeserialize(using = LocalDateJsonDeserializer.class)
    @JsonSerialize(using = LocalDateJsonSerializer.class)
    private LocalDate dateOfSale;

    private BeverageProduct beverageProduct;
    private String productName;

    private boolean isFulfilled;
    private String notFulfilledReason;

    public Sale(LocalDate dateOfSale, BeverageProduct beverageProduct, String productName) {
        this.dateOfSale = dateOfSale;
        this.beverageProduct = beverageProduct;
        this.productName = productName;
        this.isFulfilled = false;
        this.notFulfilledReason = "";
    }

    public Sale(BeverageProduct beverageProduct, String productName) {
        this(null, beverageProduct, productName);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "dateOfSale=" + dateOfSale +
                ", beverageProduct=" + beverageProduct +
                ", productName='" + productName + '\'' +
                ", isFulfilled=" + isFulfilled +
                ", notFulfilledReason='" + notFulfilledReason + '\'' +
                '}';
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public BeverageProduct getBeverageProduct() {
        return beverageProduct;
    }

    public void setBeverageProduct(BeverageProduct beverageProduct) {
        this.beverageProduct = beverageProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isFulfilled() {
        return isFulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        isFulfilled = fulfilled;
    }

    public String getNotFulfilledReason() {
        return notFulfilledReason;
    }

    public void setNotFulfilledReason(String notFulfilledReason) {
        this.notFulfilledReason = notFulfilledReason;
    }
}
