package com.tfr.microbrew.service;

import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.dao.BatchDao;
import com.tfr.microbrew.model.Batch;
import com.tfr.microbrew.model.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *
 * Created by Erik on 4/29/2017.
 */
@Service("BatchService")
public class BatchServiceImpl implements BatchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final AtomicInteger batchId = new AtomicInteger(1000);    private BatchDao batchDao;

    private static final Map<String, Recipe> RECIPES = new HashMap<>();

    @Autowired
    public BatchServiceImpl(BatchDao batchDao,
                            @Qualifier("RecipesList") List<Recipe> recipes) {
        this.batchDao = batchDao;
        recipes.forEach(r -> RECIPES.put(r.getName(), r));
    }

    @Override
    public void addBatch(String recipeName) {
        Batch batch = getBatch(recipeName);
        addBatch(batch);
    }

    @Override
    public void addBatch(Batch batch) {
        batchDao.create(batch);
    }

    @Override
    public Set<Batch> getAllInProgress() {
        return batchDao.readAll();
    }

    @Override
    public Set<Batch> getByStep(BrewStep brewStep) {
        return batchDao.readByStep(brewStep);
    }

    @Override
    public Set<Batch> getByRecipe(String recipeName) {
        return batchDao.readByRecipe(recipeName);
    }

    @Override
    public Set<Batch> getByRecipeAndStep(String recipe, BrewStep brewStep) {
        return batchDao.readByRecipeAndStep(recipe, brewStep);
    }

    @Override
    public void updateBatch(Batch batch) {
        batchDao.update(batch);
    }

    @Override
    public void deleteBatch(int batchId) {
        Batch batch = new Batch(batchId, null, null, 0);
        batchDao.delete(batch);
    }

    @Override
    public Batch getBatch(String recipeName) {
        if(!RECIPES.containsKey(recipeName)) {
            String message = String.format("Recipe %s not found", recipeName);
            logger.error(message);
            throw new RuntimeException(message);
        }
        Recipe recipe = RECIPES.get(recipeName);
        Batch batch = new Batch(batchId.getAndIncrement(), recipe, BrewStep.TO_BREW, 0);
        logger.debug(String.format("Created new batch: %s", batch));
        return batch;
    }
}
