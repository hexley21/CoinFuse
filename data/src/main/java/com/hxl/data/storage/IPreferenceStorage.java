package com.hxl.data.storage;

/**
 * Preference Storage interface for handling preference-fields by each data type.
 */
public interface IPreferenceStorage {

    boolean get(String key, boolean def);

    int get(String key, int def);

    String get(String key, String def);

    void save(String key, boolean value);

    void save(String key, int value);

    void save(String key, String value);

}
