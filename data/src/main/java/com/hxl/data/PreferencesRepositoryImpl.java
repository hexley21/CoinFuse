package com.hxl.data;

import com.hxl.data.repository.prefs.PreferencesSource;
import com.hxl.domain.repository.PreferencesRepository;

/**
 * Repository implementation that handles Preference Storage fields.
 */
public class PreferencesRepositoryImpl implements PreferencesRepository {

    private final PreferencesSource source;

    public PreferencesRepositoryImpl(PreferencesSource source) {
        this.source = source;
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
