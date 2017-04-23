package com.tfr.microbrew.compare;

import com.tfr.microbrew.model.Batch;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * Compares batches ready to carbonate based on how long they have been fermenting
 *
 * Created by Erik on 4/23/2017.
 */
@Component("CarbonationPriorityComparator")
public class CarbonationPriorityComparator implements Comparator<Batch> {

    @Override
    public int compare(Batch b1, Batch b2) {
        if(b1.getDaysInStep() > b2.getDaysInStep()) {
            return -1;
        } else if(b1.getDaysInStep() < b2.getDaysInStep()) {
            return 1;
        }
        return 0;
    }
}
