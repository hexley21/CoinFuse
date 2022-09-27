package com.hxl.data.storage.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.hxl.data.storage.IPreferenceStorage;

/**
 * Preference Storage implementation using Shared Preferences for handling preference-fields.
 */
public class SharedPreferenceStorage implements IPreferenceStorage {

    SharedPreferences sharedPreferences;

    public SharedPreferenceStorage(Context context) {
        sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    @Override
    public boolean get(String key, boolean def) {
        return sharedPreferences.getBoolean(key, def);
    }

    @Override
    public int get(String key, int def) {
        return sharedPreferences.getInt(key, def);
    }

    @Override
    public String get(String key, String def) {
        return sharedPreferences.getString(key, def);
    }

    @Override
    public void save(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public void save(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    @Override
    public void save(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

}
