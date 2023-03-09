package com.hxl.local;

import android.content.SharedPreferences;

import com.hxl.data.repository.prefs.PreferencesLocal;

public class PreferencesLocalImpl implements PreferencesLocal {

    SharedPreferences sharedPreferences;

    public PreferencesLocalImpl(SharedPreferences preferences) {
        this.sharedPreferences = preferences;
    }

    @Override
    public int get(String key, int def) {
        return sharedPreferences.getInt(key, def);
    }

    @Override
    public boolean get(String key, boolean def) {
        return sharedPreferences.getBoolean(key, def);
    }

    @Override
    public String get(String key, String def) {
        return sharedPreferences.getString(key, def);
    }

    @Override
    public void save(String key, Integer pref) {
        sharedPreferences.edit().putInt(key, pref).apply();
    }

    @Override
    public void save(String key, Boolean pref) {
        sharedPreferences.edit().putBoolean(key, pref).apply();
    }

    @Override
    public void save(String key, String pref) {
        sharedPreferences.edit().putString(key, pref).apply();
    }

}
