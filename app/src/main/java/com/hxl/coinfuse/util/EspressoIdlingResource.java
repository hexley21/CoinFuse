package com.hxl.coinfuse.util;

import androidx.test.espresso.idling.CountingIdlingResource;

public final class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    public static final CountingIdlingResource idlingResource = new CountingIdlingResource(RESOURCE);

    public static void increment() {
        idlingResource.increment();
    }

    public static void decrement() {
        if (!idlingResource.isIdleNow()) {
            idlingResource.decrement();
        }
    }
}
