package com.tfr.microbrew.service;

import com.tfr.microbrew.exception.InventoryException;
import com.tfr.microbrew.model.InventoryItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests for InventoryService
 *
 * Created by Erik on 4/22/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestInventoryServiceImpl {

    @Autowired
    private InventoryService inventoryService;

    @Before
    public void setUp() {
        inventoryService.deleteItem("TestItem1");
        inventoryService.deleteItem("TestItem2");
        InventoryItem inventoryItem1 = new InventoryItem("TestItem1", 100.0, 50.0, 250.0);
        InventoryItem inventoryItem2 = new InventoryItem("TestItem2", 155.0, 100.0, 200.0);
        inventoryService.addItem(inventoryItem1);
        inventoryService.addItem(inventoryItem2);
    }

    @Test
    public void testAddAndGet() {
        InventoryItem inventoryItem = inventoryService.getItemByName("TestItem1");
        assertNotNull(inventoryItem);
        assertEquals("TestItem1", inventoryItem.getName());
        assertEquals(100.0, inventoryItem.getQuantity(), 0.01);
        assertEquals(50.0, inventoryItem.getReorderThreshold(), 0.01);
        assertEquals(250.0, inventoryItem.getReorderQuantity(), 0.01);
    }

    @Test(expected = InventoryException.class)
    public void testAdd_ExpectException() {
        InventoryItem inventoryItem1 = new InventoryItem("TestItem1", 100.0, 50.0, 250.0);
        inventoryService.addItem(inventoryItem1);
    }

    @Test
    public void testUpdateQuantity_Add() {
        inventoryService.updateQuantity("TestItem1", 10.0);
        InventoryItem inventoryItem = inventoryService.getItemByName("TestItem1");
        assertNotNull(inventoryItem);
        assertEquals("TestItem1", inventoryItem.getName());
        assertEquals(110.0, inventoryItem.getQuantity(), 0.01);
    }

    @Test
    public void testUpdateQuantity_Remove() {
        inventoryService.updateQuantity("TestItem1", -10.0);
        InventoryItem inventoryItem = inventoryService.getItemByName("TestItem1");
        assertNotNull(inventoryItem);
        assertEquals("TestItem1", inventoryItem.getName());
        assertEquals(90.0, inventoryItem.getQuantity(), 0.01);
    }

    @Test(expected = InventoryException.class)
    public void testUpdateQuantity_ExpectException() {
        inventoryService.updateQuantity("TestItem3", 10.0);
    }

    @Test
    public void testDelete() {
        inventoryService.deleteItem("TestItem1");
        InventoryItem inventoryItem = inventoryService.getItemByName("TestItem1");
        assertNull(inventoryItem);
    }

}

