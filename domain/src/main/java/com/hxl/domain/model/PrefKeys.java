package com.hxl.domain.model;

public final class PrefKeys<T> {

    public static final PrefKeys<Boolean> WELCOME = new PrefKeys<>("welcome", true);
    public static final PrefKeys<Integer> THEME = new PrefKeys<>("theme", -1);
    public static final PrefKeys<String> LANGUAGE = new PrefKeys<>("language", "en");

    public final String key;
    public final T def;

    public PrefKeys(String key, T def) {
        this.key = key;
        this.def = def;
    }

}
