package com.tfr.microbrew.model;

/**
 *
 * Created by Erik Hage on 5/13/2017.
 */
public class ViewContext {

    private final Context context;

    public ViewContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
