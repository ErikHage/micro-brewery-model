package com.tfr.microbrew.config;

/**
 *
 * Created by Erik Hage on 5/13/2017.
 */
public interface Routes {

    String INDEX = "/index";

    String CONTEXT_SUMMARY = "/{contextId}/summary";

    String DAY_VIEW_START = "/dayView/{contextId}";
    String DAY_VIEW = "/dayView/{contextId}/{date}";

}
