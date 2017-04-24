package com.tfr.microbrew.probability;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.*;

/**
 * An object to help generate responses based on given probabilities. A response can be added with a corresponding
 * weight. If the total weights do not add up to 100 they will be normalized so their total equals 100. For example,
 * two items with weights of 10 and 40 (a total weight of 50) would be normalized to 20 and 80, respectively. Two
 * items with weights of 50 and 150 would be normalized to 25 and 75, respectively.
 *
 * Created by Erik on 4/11/2017.
 */
public class NormalizedProbability<T> {

    private boolean isNormalized;
    private int roundingScale;
    private NavigableMap<Double, T> probability;
    private List<Item> items;
    private Random random;

    /**
     * Instantiate a new NormalizedProbability with a specified rounding scale
     *
     * @param roundingScale - number of decimal places to round weights after normalization
     */
    public NormalizedProbability(int roundingScale) {
        this.items = new ArrayList<>();
        this.probability = new TreeMap<>();
        this.roundingScale = roundingScale;
        this.isNormalized = false;
        this.random = new Random();
    }

    /**
     * Instantiate a new NormalizedProbability with the default rounding scale of 2 decimal places
     *
     */
    public NormalizedProbability() {
        this(4);
    }

    /**
     * Add a new response to the possible responses and recalculate the normalization of weights
     *
     * @param weight - chance for the corresponding value to be returned
     * @param value - response to be returned
     */
    public void add(Double weight, T value) {
        items.add(new Item(weight, value));
        refreshMap();
    }

    /**
     * Returns one of the added items with a probability based on their normalized weights
     *
     * @return - a response from the added items
     */
    public T getRandom() {
        return this.get(random.nextDouble()*100);
    }

    /**
     * Returns one of the added items with a probability based on the normalized weight provided
     *
     * @return - a response from the added items
     */
    public T get(Double roll) {
        Map.Entry<Double, T> entry;
        roll = new BigDecimal(roll).setScale(roundingScale, BigDecimal.ROUND_HALF_UP).doubleValue();

        if(roll < 0 || roll > 100) {
            throw new InvalidParameterException("Value must be between 0 and 100");
        }

        entry = probability.ceilingEntry(roll);

        return entry.getValue();
    }

    /**
     * Refreshes the NavigableMap, recalculating the normalized weights of all added items
     */
    private void refreshMap() {
        Double cumulative = 0.0;
        Double normalizationFactor = getNormalizationFactor();
        probability.clear();

        isNormalized = normalizationFactor != 1;

        for(Item i: items) {
            Double normalizaedWeight = i.weight * normalizationFactor;
            probability.put(cumulative += normalizaedWeight, i.value);
        }
    }

    /**
     * Returns the current normalization factor used to normalize the total weight of the added items to 100
     *
     * @return - double - Normalization Factor
     */
    public Double getNormalizationFactor() {
        Double normalization = 0.0;
        for(Item i: items) {
            normalization += i.weight;
        }
        return 100/normalization;
    }

    /**
     * Returns true if the total item weight is not equal to 100 and therefore required normalization otherwise
     * returns false
     *
     * @return - boolean - if the total weights required normalization
     */
    public boolean isNormalized() {
        return isNormalized;
    }

    /**
     * Returns the rounding scale this instance of NormalizedProbability was created with
     *
     * @return - int - rounding scale
     */
    public int getRoundingScale() {
        return roundingScale;
    }

    /**
     * A single item and its corresponding weight
     */
    private class Item {
        Double weight;
        T value;
        private Item(Double weight, T value) {
            this.weight = weight;
            this.value = value;
        }
    }
}
