package com.tfr.microbrew.config;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
public interface Constants {

    interface RecipeNames {
        String CHECKS_AND_BALANCES_IPA = "Checks and Balances IPA";
        String ROSIES_RED_ALE = "Rosie's Red Ale";
        String COLD_BREW_COFEE_PORTER = "Cold Brew Coffee Porter";
        String TRIPPLECANOE_AND_TYLER_TOO = "Tripplecanoe and Tyler Too";
        String WIT_OF_THEIR_EYES = "Wit of Their Eyes";
        String AMBER_WAVES_OF_GRAIN = "Amber Waves of Grain";
        String SUMMER_SMASH_IPA = "Summer SMaSH IPA";
    }

    Set<String> ACTIVE_PRODUCTS = Sets.newHashSet(
            RecipeNames.CHECKS_AND_BALANCES_IPA,
            RecipeNames.ROSIES_RED_ALE,
            RecipeNames.COLD_BREW_COFEE_PORTER,
            RecipeNames.TRIPPLECANOE_AND_TYLER_TOO
    );

    interface BrewHouse {
        double BATCH_SIZE = 93.0;
        int FERMENETERS = 8;
        int CARBONATION_VESSELS = 2;
    }

    interface ProductVolumes {
        Double PINT = 0.125;
        Double FLIGHT = 0.25;
        Double HALF_GROWLER = 0.5;
        Double GROWLER = 1.0;
    }

    Set<DayOfWeek> BREW_DAYS = Sets.newHashSet(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY
    );

    Set<DayOfWeek> PROCESSING_DAYS = Sets.newHashSet(
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

}
