package com.tfr.microbrew.service;


import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.model.Batch;

import java.util.Set;

/**
 *
 *
 * Created by Erik on 4/28/2017.
 */
public interface BatchService {

    void addBatch(String recipeName);
    void addBatch(Batch batch);

    Set<Batch> getAllInProgress();
    Set<Batch> getByStep(BrewStep brewStep);
    Set<Batch> getByRecipe(String recipeName);
    Set<Batch> getByRecipeAndStep(String recipe, BrewStep step);

    void updateBatch(Batch batch);

    void deleteBatch(int batchId);

}
