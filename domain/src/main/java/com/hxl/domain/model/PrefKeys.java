package com.hxl.domain.model;

import java.util.Currency;

public final class PrefKeys<T> {

    public static PrefKeys<Boolean> WELCOME = new PrefKeys<>("welcome", true);
    public static PrefKeys<Integer> THEME = new PrefKeys<>("theme", -1);
    public static PrefKeys<Long> LAST_SAVE = new PrefKeys<>("last_save", 0L);
    public static PrefKeys<String> CURRENCY = new PrefKeys<>("currency", "united-states-dollar");
    public static PrefKeys<String> LANGUAGE = new PrefKeys<>("language", "en");

    public String key;
    public T def;

    public PrefKeys(String key, T def) {
        this.key = key;
        this.def = def;
    }

}
