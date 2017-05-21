package com.tfr.microbrew.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * Created by Erik on 4/24/2017.
 */
public enum BeverageVolume {

    @JsonProperty("PINT")
    PINT("PINT"),

    @JsonProperty("SAMPLE")
    SAMPLE("SAMPLE"),

    @JsonProperty("FLIGHT")
    FLIGHT("FLIGHT"),

    @JsonProperty("HOWLER")
    HOWLER("HOWLER"),

    @JsonProperty("GROWLER")
    GROWLER("GROWLER");

    private String value;

    BeverageVolume(String value) {
        this.value = value;
    }

    public static BeverageVolume getFromValue(String value) {
        for(BeverageVolume v : BeverageVolume.values()) {
            if(v.value.equals(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Given value invalid: " + value);
    }

    public String getValue() {
        return value;
    }

}
