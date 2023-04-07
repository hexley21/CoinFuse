package com.hxl.domain.model;

public final class PrefKeys<T> {

    public static PrefKeys<Boolean> WELCOME = new PrefKeys<>("welcome", true);
    public static PrefKeys<Integer> THEME = new PrefKeys<>("theme", -1);
    public static PrefKeys<String> CURRENCY = new PrefKeys<>("currency", "united-states-dollar");
    public static PrefKeys<String> LANGUAGE = new PrefKeys<>("language", "en");

    public String key;
    public T def;

    public PrefKeys(String key, T def) {
        this.key = key;
        this.def = def;
    }

}
