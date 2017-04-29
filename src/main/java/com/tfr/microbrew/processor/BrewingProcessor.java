package com.tfr.microbrew.processor;

import com.tfr.microbrew.compare.BrewPriorityComparator;
import com.tfr.microbrew.config.BrewStep;
import com.tfr.microbrew.config.Constants;
import com.tfr.microbrew.config.DayOfWeek;
import com.tfr.microbrew.dao.BatchDao;
import com.tfr.microbrew.model.Batch;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 *
 * Created by Erik on 4/28/2017.
 */
@Component("BrewingProcessor")
public class BrewingProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BatchDao batchDao;
    private BrewPriorityComparator brewPriorityComparator;

    @Autowired
    public BrewingProcessor(BatchDao batchDao,
                            BrewPriorityComparator brewPriorityComparator) {
        this.batchDao = batchDao;
        this.brewPriorityComparator = brewPriorityComparator;
    }

    @Override
    public void process(LocalDate date) {
        logger.debug("Brewing Day");
        //if fermenter available, brew a batch from the TO_BREW batches and start fermentation
        if(batchDao.readByStep(BrewStep.TO_BREW).size() > 0) {
            if (batchDao.readByStep(BrewStep.FERMENT).size() < Constants.BrewHouse.FERMENTERS) {
                //sort by brewPriority
                SortedSet<Batch> batchesToBrew = new TreeSet<>(brewPriorityComparator);
                batchesToBrew.addAll(batchDao.readByStep(BrewStep.TO_BREW));
                Batch batchToBrew = batchesToBrew.first();

                //update progress of batch
                batchToBrew.setCurrentStep(BrewStep.FERMENT);
                batchToBrew.setDaysInStep(0);

                logger.debug(String.format("Batch of %s was brewed and is now fermenting",
                        batchToBrew.getRecipe().getName()));
            } else {
                logger.debug("No Space in fermenters to brew a new batch today");
            }
        } else {
            logger.debug("No batches to brew in queue today");
        }

        //TODO update inventory for the recipe ingredients
    }
}
