package com.tfr.microbrew.model;

import com.tfr.microbrew.config.BrewStep;

/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
public class Batch {

    private int batchId;
    private Recipe recipe;
    private BrewStep currentStep;
    private int daysInStep;

    public Batch() {
        //for Jackson parsing
    }

    public Batch(int batchId, Recipe recipe, BrewStep currentStep, int daysInStep) {
        this.batchId = batchId;
        this.recipe = recipe;
        this.currentStep = currentStep;
        this.daysInStep = daysInStep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Batch batch = (Batch) o;

        return batchId == batch.batchId;
    }

    @Override
    public int hashCode() {
        return batchId;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batchId=" + batchId +
                ", recipe=" + recipe.getName() +
                ", currentStep=" + currentStep +
                ", daysInStep=" + daysInStep +
                '}';
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public BrewStep getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(BrewStep currentStep) {
        this.currentStep = currentStep;
    }

    public int getDaysInStep() {
        return daysInStep;
    }

    public void setDaysInStep(int daysInStep) {
        this.daysInStep = daysInStep;
    }
}
