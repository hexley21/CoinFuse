package com.hxl.domain.usecase.prefs;

import com.hxl.domain.repository.IPreferenceRepository;

public class SaveTheme {

    private final IPreferenceRepository preferenceRepository;

    public SaveTheme(IPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(int theme) {
        preferenceRepository.saveTheme(theme);
    }

}
