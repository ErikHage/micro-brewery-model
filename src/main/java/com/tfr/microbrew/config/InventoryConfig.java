package com.tfr.microbrew.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.tfr.microbrew.model.InventoryItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 *
 *
 * Created by Erik on 4/23/2017.
 */
@Configuration
public class InventoryConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String DATA_FILE = "config/inventory.json";

    @Bean(name = "InventoryItems")
    public Set<InventoryItem> inventoryItems() {
        Set<InventoryItem> items;

        try {
            URL url = Resources.getResource(DATA_FILE);
            String json = Resources.toString(url, Charsets.UTF_8);
            items = objectMapper.readValue(json, new TypeReference<Set<InventoryItem>>(){});
            logger.debug("Loaded inventory config from " + DATA_FILE);
        } catch(IOException ex) {
            logger.error("Exception occurred when loading inventory configuration from " + DATA_FILE, ex);
            throw new RuntimeException("Exception occurred when loading inventory configuration from " + DATA_FILE);
        }

        return items;
    }

}
