package com.tfr.microbrew.config;

import com.tfr.microbrew.MicroBreweryModelApplication;
import com.tfr.microbrew.model.InventoryItem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = MicroBreweryModelApplication.class)
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
