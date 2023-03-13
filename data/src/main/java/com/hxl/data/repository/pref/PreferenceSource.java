package com.hxl.data.repository.pref;


public interface PreferenceSource {
    int get(String key, int def);
    boolean get(String key, boolean def);
    String get(String key, String def);
    void save(String key, Integer pref);
    void save(String key, Boolean pref);
    void save(String key, String pref);

}
