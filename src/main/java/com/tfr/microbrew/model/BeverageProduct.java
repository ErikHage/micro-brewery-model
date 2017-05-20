package com.tfr.microbrew.model;

import com.tfr.microbrew.config.BeverageVolume;

/**
 *
 * Created by Erik on 4/24/2017.
 */
public class BeverageProduct {

    private BeverageVolume beverageVolume;
    private Double volume;
    private Double probability;

    public BeverageProduct() {
        //for Jackson parsing
    }

    public BeverageProduct(BeverageVolume beverageVolume, Double volume, Double probability) {
        this.beverageVolume = beverageVolume;
        this.volume = volume;
        this.probability = probability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeverageProduct that = (BeverageProduct) o;

        if (beverageVolume != that.beverageVolume) return false;
        if (volume != null ? !volume.equals(that.volume) : that.volume != null) return false;
        return probability != null ? probability.equals(that.probability) : that.probability == null;
    }

    @Override
    public int hashCode() {
        int result = beverageVolume != null ? beverageVolume.hashCode() : 0;
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        result = 31 * result + (probability != null ? probability.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BeverageProduct{" +
                "beverageVolume=" + beverageVolume +
                ", volume=" + volume +
                ", probability=" + probability +
                '}';
    }

    public BeverageVolume getBeverageVolume() {
        return beverageVolume;
    }

    public void setBeverageVolume(BeverageVolume beverageVolume) {
        this.beverageVolume = beverageVolume;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
}
