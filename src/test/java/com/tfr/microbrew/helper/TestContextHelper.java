package com.tfr.microbrew.helper;

import com.tfr.microbrew.model.Context;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * Created by Erik Hage on 5/13/2017.
 */
public class TestContextHelper {

    @Test
    public void testGetContextIds() {
        List<String> contextIds = ContextHelper.getContextIds();

        assertNotNull(contextIds);
        assertTrue(contextIds.contains("test"));
    }

    @Test
    public void getInitialContextData() {
        Context context = ContextHelper.getInitialContextData("test2");

        assertNotNull(context);
        assertNotNull(context.getDate());
        assertNotNull(context.getBatches());
        assertNotNull(context.getInventory());
        assertNotNull(context.getSales());
        assertEquals("2017-04-01", context.getDate().toString("yyyy-MM-dd"));
    }

    @Test
    public void testGetContextData() {
        Context context = ContextHelper.getContextData("test","2017-04-01");

        assertNotNull(context);
        assertNotNull(context.getDate());
        assertNotNull(context.getBatches());
        assertNotNull(context.getInventory());
        assertNotNull(context.getSales());
    }



}
