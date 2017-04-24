package com.tfr.microbrew.compare;

import com.tfr.microbrew.model.InventoryItem;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * Created by Erik on 4/24/2017.
 */
@Component("InventoryItemCategoryBasedComparator")
public class InventoryItemCategoryBasedComparator implements Comparator<InventoryItem> {
    @Override
    public int compare(InventoryItem item1, InventoryItem item2) {
        int value = item1.getCategory().compareTo(item2.getCategory());
        if(value == 0) {
            value = item1.getName().compareTo(item2.getName());
        }
        return value;
    }
}
