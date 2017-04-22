package com.tfr.microbrew.config;

/**
 * Enum representing days of the week
 *
 * Created by Erik on 4/22/2017.
 */
public enum DayOfWeek {

    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    private int value;
    private String name;

    private final String[] names = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday", "Sunday"};

    DayOfWeek(int value) {
        this.value = value;
        this.name = names[value-1];
    }

    public static DayOfWeek getFromInt(int value) {
        for(DayOfWeek d : DayOfWeek.values()) {
            if(d.value == value) {
                return d;
            }
        }
        throw new IllegalArgumentException("Value must between 1 and 7");
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
