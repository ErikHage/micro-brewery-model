package com.tfr.microbrew.config;

import com.tfr.microbrew.model.InventoryItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Tests for InventoryConfig
 *
 * Created by Erik on 5/3/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestInventoryConfig {

    @Resource(name = "InventoryItems")
    private Set<InventoryItem> inventoryItems;

    @Test
    public void testContextLoads() {
        assertNotNull(inventoryItems);
    }

    @Test
    public void testJacksonMapping() {
        InventoryItem item = inventoryItems.stream().findFirst().orElse(null);
        assertNotNull(item);
        assertNotNull(item.getName());
        assertNotNull(item.getCategory());
        assertNotNull(item.getQuantity());
        assertNotNull(item.getReorderThreshold());
        assertNotNull(item.getReorderQuantity());
    }

}
