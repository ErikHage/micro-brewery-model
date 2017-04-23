package com.tfr.microbrew.dao;

import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.model.Batch;

import java.util.Set;

/**
 *
 * Created by Erik on 4/22/2017.
 */
public interface BatchDao {

    void create(Batch batch);
    Set<Batch> readAll();
    Set<Batch> readByStep(BrewStep brewStep);
    Set<Batch> readByRecipe(String recipeName);
    Set<Batch> readByRecipeAndStep(String recipe, BrewStep brewStep);
    void update(Batch batch);
    void delete(Batch batch);

}
