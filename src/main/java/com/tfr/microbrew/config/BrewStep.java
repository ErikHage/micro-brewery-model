package com.tfr.microbrew.config;

/**
 * Steps in the Brewing Process
 *
 * Created by Erik on 4/22/2017.
 */
public enum BrewStep {

    TO_BREW("TO_BREW"),
    FERMENT("FERMENT"),
    CARBONATE("CARBONATE"),
    PACKAGE("PACKAGE");

    private final String value;

    BrewStep(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
