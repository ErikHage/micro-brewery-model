package com.tfr.microbrew.config;

/**
 * Ingredient Names
 *
 * Created by Erik on 4/30/2017.
 */
public interface Ingredients {

    interface Grain {
        String AMERICAN_2_ROW = "American 2-Row Pale Malt";
        String BELGIAN_2_ROW = "Belgian 2-Row Pale Malt";
        String PILSEN_2_ROW = "Pilsen 2-Row Malt";
        String CARAMEL_20L = "Caramel 20L Malt";
        String CARAMEL_40L = "Caramel 40L Malt";
        String CARAMEL_60L = "Caramel 60L Malt";
        String BISCUIT = "Biscuit Malt";
    }

    interface Hop {
        String STYRIAN_GOLDINGS = "Styrian Goldings";
        String KENT_GOLDINGS = "Kent Goldings";
        String CASCADE = "Cascade";
        String CITRA = "Citra";
    }

    interface Yeast {
        String WHITELABS_SAISON = "White Labs Saison";
        String WHITELABS_WITBIER = "White Labs Witbier";
        String SAFALE_AMERICAN_ALE = "Safale American Ale";
        String SAFALE_ENGLISH_ALE = "Safale English Ale";
    }

    interface Adjunct {
        String IRISH_MOSS = "Irish Moss";
        String LIGHT_CANDI_SUGAR = "Light Belgian Candi Sugar";
        String DARK_CANDI_SUGAR = "Dark Belgian Candi Sugar";
    }

}
