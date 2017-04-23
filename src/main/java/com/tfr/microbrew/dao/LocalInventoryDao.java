package com.tfr.microbrew.dao;

import com.tfr.microbrew.config.InventoryConfig;
import com.tfr.microbrew.model.InventoryItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
@Repository("LocalInventoryDao")
public class LocalInventoryDao implements InventoryDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Map<String, InventoryItem> INVENTORY = new HashMap<>();

    public LocalInventoryDao() {
        InventoryConfig.DEFAULT_ITEMS.forEach(item -> INVENTORY.put(item.getName(), item));
    }

    @Override
    public void create(InventoryItem inventoryItem) {
        INVENTORY.put(inventoryItem.getName(), inventoryItem);
    }

    @Override
    public InventoryItem readByName(String name) {
        return INVENTORY.get(name);
    }

    @Override
    public void update(InventoryItem inventoryItem) {
        INVENTORY.put(inventoryItem.getName(), inventoryItem);
    }

    @Override
    public void delete(InventoryItem inventoryItem) {
        INVENTORY.remove(inventoryItem.getName());
    }
}
