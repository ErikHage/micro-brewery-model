package com.tfr.microbrew.config;

import org.springframework.stereotype.Component;

/**
 *
 * Created by Erik on 5/8/2017.
 */
@Component("ContextId")
public class ContextId {

    private long contextId;

    public long getContextId() {
        return contextId;
    }

    public void setContextId(long contextId) {
        this.contextId = contextId;
    }
}
