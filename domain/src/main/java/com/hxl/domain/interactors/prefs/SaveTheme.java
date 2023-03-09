package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.THEME;

import com.hxl.domain.repository.PreferencesRepository;

public class SaveTheme {

    private final PreferencesRepository preferenceRepository;

    public SaveTheme(PreferencesRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(int theme) {
        preferenceRepository.save(THEME.key, theme);
    }

}
