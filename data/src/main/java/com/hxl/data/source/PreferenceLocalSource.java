package com.hxl.data.source;

import com.hxl.data.repository.prefs.PreferenceSource;
import com.hxl.data.repository.prefs.PreferenceLocal;

public class PreferenceLocalSource implements PreferenceSource {

    private final PreferenceLocal source;

    public PreferenceLocalSource(PreferenceLocal localSource) {
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
