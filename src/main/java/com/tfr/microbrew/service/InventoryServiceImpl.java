package com.tfr.microbrew.service;

import com.tfr.microbrew.dao.InventoryDao;
import com.tfr.microbrew.exception.InventoryException;
import com.tfr.microbrew.model.InventoryItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
@Service("InventoryServiceImpl")
public class InventoryServiceImpl implements InventoryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final InventoryDao inventoryDao;

    @Autowired
    public InventoryServiceImpl(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    @Override
    public void addItem(InventoryItem inventoryItem) throws InventoryException {
        if(getItemByName(inventoryItem.getName()) != null) {
            logger.error(String.format("Cannot add %s, item with same name already exists in inventory", inventoryItem.getName()));
            throw new InventoryException(String.format("Cannot add %s, item with same name already exists in inventory", inventoryItem.getName()));
        }
        logger.debug(String.format("Adding new item: %s", inventoryItem));
        inventoryDao.create(inventoryItem);
    }

    @Override
    public InventoryItem getItemByName(String name) {
        logger.debug(String.format("Looking up item: %s", name));
        return inventoryDao.readByName(name);
    }

    @Override
    public double getCurrentQuantity(String name) {
        logger.debug(String.format("Looking up current quantity for item: %s", name));
        InventoryItem inventoryItem = getItemByName(name);
        if(inventoryItem == null) {
            return 0.0;
        }
        return inventoryItem.getQuantity();
    }

    @Override
    public void updateItem(InventoryItem inventoryItem) {
        logger.debug(String.format("Updating item to: %s", inventoryItem));
        inventoryDao.update(inventoryItem);
    }

    @Override
    public void updateQuantity(String name, double changeInQuantity) throws InventoryException {
        InventoryItem inventoryItem = inventoryDao.readByName(name);
        if(inventoryItem == null) {
            logger.error(String.format("Cannot update %s by %s units, does not exist in inventory", name, changeInQuantity));
            throw new InventoryException(String.format("Cannot update %s by %s units, does not exist in inventory",
                    name, changeInQuantity));
        }
        double newQuantity = inventoryItem.getQuantity() + changeInQuantity;
        logger.debug(String.format("Updating quantity for %s from %s to %s",
                inventoryItem.getName(), inventoryItem.getQuantity(), newQuantity));
        inventoryItem.setQuantity(newQuantity);
        inventoryDao.update(inventoryItem);
    }

    @Override
    public void deleteItem(String name) {
        logger.debug(String.format("Deleting item: %s", name));
        inventoryDao.delete(new InventoryItem(name, 0.0, 0.0));
    }
}
