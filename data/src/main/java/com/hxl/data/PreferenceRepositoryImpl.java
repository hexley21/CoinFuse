package com.hxl.data;

import com.hxl.data.repository.pref.PreferenceLocal;
import com.hxl.domain.repository.PreferenceRepository;

/**
 * Repository implementation that handles Preference Storage fields.
 */
public class PreferenceRepositoryImpl implements PreferenceRepository {

    private final PreferenceLocal localSource;

    public PreferenceRepositoryImpl(PreferenceLocal localSource) {
        this.localSource = localSource;
    }

    @Override
    public int get(String key, int def) {
        return localSource.get(key, def);
    }

    @Override
    public boolean get(String key, boolean def) {
        return localSource.get(key, def);
    }

    @Override
    public String get(String key, String def) {
        return localSource.get(key, def);
    }

    @Override
    public void save(String key, Integer pref) {
        localSource.save(key, pref);
    }

    @Override
    public void save(String key, Boolean pref) {
        localSource.save(key, pref);
    }

    @Override
    public void save(String key, String pref) {
        localSource.save(key, pref);
    }
}
