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

    private double price;

    public Sale() {
        //for Jackson parsing
    }

    public Sale(LocalDate dateOfSale, BeverageProduct beverageProduct, String productName, double price) {
        this.dateOfSale = dateOfSale;
        this.beverageProduct = beverageProduct;
        this.productName = productName;
        this.price = price;
        this.isFulfilled = false;
        this.notFulfilledReason = "";
    }

    public Sale(BeverageProduct beverageProduct, String productName, double price) {
        this(null, beverageProduct, productName, price);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "dateOfSale=" + dateOfSale +
                ", beverageProduct=" + beverageProduct +
                ", productName='" + productName + '\'' +
                ", isFulfilled=" + isFulfilled +
                ", notFulfilledReason='" + notFulfilledReason + '\'' +
                ", price=" + price +
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
