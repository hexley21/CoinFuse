package com.hxl.data.source;

import com.hxl.data.repository.prefs.PreferencesLocal;
import com.hxl.data.repository.prefs.PreferencesSource;

public class PreferencesLocalSource implements PreferencesSource {

    private final PreferencesLocal source;

    public PreferencesLocalSource(PreferencesLocal localSource) {
        this.source = localSource;
    }

    @Override
    public int get(String key, int def) {
        return source.get(key, def);
    }

    @Override
    public boolean get(String key, boolean def) {
        return source.get(key, def);
    }

    @Override
    public String get(String key, String def) {
        return source.get(key, def);
    }

    @Override
    public void save(String key, Integer pref) {
        source.save(key, pref);
    }

    @Override
    public void save(String key, Boolean pref) {
        source.save(key, pref);
    }

    @Override
    public void save(String key, String pref) {
        source.save(key, pref);
    }
}
