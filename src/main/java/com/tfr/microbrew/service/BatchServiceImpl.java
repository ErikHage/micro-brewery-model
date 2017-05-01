package com.tfr.microbrew.service;

import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.dao.BatchDao;
import com.tfr.microbrew.helper.BatchHelper;
import com.tfr.microbrew.model.Batch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 *
 *
 * Created by Erik on 4/29/2017.
 */
@Service("BatchService")
public class BatchServiceImpl implements BatchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BatchDao batchDao;

    @Autowired
    public BatchServiceImpl(BatchDao batchDao) {
        this.batchDao = batchDao;
    }

    @Override
    public void addBatch(String recipeName) {
        Batch batch = BatchHelper.getBatch(recipeName);
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
}
