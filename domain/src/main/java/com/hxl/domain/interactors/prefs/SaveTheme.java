package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.THEME;

import com.hxl.domain.repository.PreferenceRepository;

public class SaveTheme {

    private final PreferenceRepository preferenceRepository;

    public SaveTheme(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(int theme) {
        preferenceRepository.save(THEME.key, theme);
    }

}
