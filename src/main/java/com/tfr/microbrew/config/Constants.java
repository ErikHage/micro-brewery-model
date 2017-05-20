package com.tfr.microbrew.config;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
public interface Constants {

    interface BrewHouse {
        double BATCH_SIZE = 93.0;
        int FERMENTING_VESSELS = 8;
        int CARBONATION_VESSELS = 2;
    }

    interface InventoryCategory {
        String GRAIN = "Grain";
        String HOP = "Hop";
        String YEAST = "Yeast";
        String ADJUNCT = "Adjunct";
        String BEER = "Beer";
    }

    Set<DayOfWeek> BREW_DAYS = Sets.newHashSet(
//            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY
    );

    Set<DayOfWeek> TRANSFER_DAYS = Sets.newHashSet(
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY
    );

    Set<DayOfWeek> BUSINESS_DAYS = Sets.newHashSet(
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY
    );

    Set<DayOfWeek> RESTOCK_INVENTORY_DAYS = Sets.newHashSet(
            DayOfWeek.MONDAY
    );

}
