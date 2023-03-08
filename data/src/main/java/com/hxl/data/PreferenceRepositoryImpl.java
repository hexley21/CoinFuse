package com.hxl.data;

import com.hxl.data.storage.IPreferenceStorage;
import com.hxl.domain.repository.IPreferenceRepository;

/**
 * Repository implementation that handles Preference Storage fields.
 */
public class PreferenceRepositoryImpl implements IPreferenceRepository{

    IPreferenceStorage preferenceStorage;

    public PreferenceRepositoryImpl(IPreferenceStorage preferenceStorage) {
        this.preferenceStorage = preferenceStorage;
    }

    @Override
    public boolean getWelcome(Boolean def) {
        return preferenceStorage.get("welcome", def);
    }

    @Override
    public void saveWelcome(boolean value) {
        preferenceStorage.save("welcome", value);
    }

    @Override
    public String getLanguage(String def) {
        return preferenceStorage.get("language", def);
    }

    @Override
    public void saveLanguage(String value) {
        preferenceStorage.save("language", value);
    }

    @Override
    public int getTheme(int def) {
        return preferenceStorage.get("theme", def);
    }

    @Override
    public void saveTheme(int value) {
        preferenceStorage.save("theme", value);
    }

    @Override
    public String getCurrency(String def) {
        return preferenceStorage.get("currency", def);
    }

    @Override
    public void saveCurrency(String value) {
        preferenceStorage.save("currency", value);
    }
}
