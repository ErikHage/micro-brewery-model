package com.tfr.microbrew.dao;

import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.model.Batch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * Created by Erik on 4/22/2017.
 */
@Repository("LocalBatchDao")
public class LocalBatchDao implements BatchDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Map<Integer, Batch> IN_PROGRESS_BATCHES = new HashMap<>();


    @Override
    public void create(Batch batch) {
        IN_PROGRESS_BATCHES.put(batch.getBatchId(), batch);
    }

    @Override
    public Set<Batch> readAll() {
        return IN_PROGRESS_BATCHES.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Batch> readByStep(BrewStep brewStep) {
        return IN_PROGRESS_BATCHES.entrySet().stream()
                .filter(e -> e.getValue().getCurrentStep().equals(brewStep))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Batch> readByRecipe(String recipeName) {
        return IN_PROGRESS_BATCHES.entrySet().stream()
                .filter(e -> e.getValue().getRecipe().getName().equals(recipeName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Batch> readByRecipeAndStep(String recipeName, BrewStep brewStep) {
        return IN_PROGRESS_BATCHES.entrySet().stream()
                .filter(e -> e.getValue().getRecipe().getName().equals(recipeName))
                .filter(e -> e.getValue().getCurrentStep().equals(brewStep))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    @Override
    public void update(Batch batch) {
        IN_PROGRESS_BATCHES.put(batch.getBatchId(), batch);
    }

    @Override
    public void delete(Batch batch) {
        IN_PROGRESS_BATCHES.remove(batch.getBatchId());
    }
}
