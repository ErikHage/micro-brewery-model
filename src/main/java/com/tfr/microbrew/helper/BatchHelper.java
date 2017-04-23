package com.tfr.microbrew.helper;

import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.config.Recipes;
import com.tfr.microbrew.model.Batch;
import com.tfr.microbrew.model.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * Created by Erik on 4/22/2017.
 */
public class BatchHelper {

    private static final Logger logger = LoggerFactory.getLogger(BatchHelper.class);
    private static final AtomicInteger batchId = new AtomicInteger(1000);

    public static Batch getBatch(String recipeName) {
        Recipe recipe = Recipes.RECIPES.get(recipeName);
        Batch batch = new Batch(batchId.getAndIncrement(), recipe, BrewStep.TO_BREW, 0);
        logger.debug(String.format("Created new batch: %s", batch));
        return batch;
    }

}
