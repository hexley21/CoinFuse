package com.hxl.domain.repository;


public interface PreferencesRepository {
    int get(String key, int def);
    boolean get(String key, boolean def);
    String get(String key, String def);

    void save(String key, Integer pref);
    void save(String key, Boolean pref);
    void save(String key, String pref);
}
