package com.tfr.microbrew.model;

import java.util.List;

/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
public class Recipe {

    private String name;
    private double volume;

    private List<Ingredient> ingredients;

    private long fermentationDays;
    private long carbonationDays;

    public Recipe(String name, double volume, List<Ingredient> ingredients,
                  long fermentationDays, long carbonationDays) {
        this.name = name;
        this.volume = volume;
        this.ingredients = ingredients;
        this.fermentationDays = fermentationDays;
        this.carbonationDays = carbonationDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        return name.equals(recipe.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", volume=" + volume +
                ", ingredients=" + ingredients +
                ", fermentationDays=" + fermentationDays +
                ", carbonationDays=" + carbonationDays +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public long getFermentationDays() {
        return fermentationDays;
    }

    public void setFermentationDays(long fermentationDays) {
        this.fermentationDays = fermentationDays;
    }

    public long getCarbonationDays() {
        return carbonationDays;
    }

    public void setCarbonationDays(long carbonationDays) {
        this.carbonationDays = carbonationDays;
    }
}
