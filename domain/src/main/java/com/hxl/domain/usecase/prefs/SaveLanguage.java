package com.hxl.domain.usecase.prefs;

import com.hxl.domain.repository.IPreferenceRepository;

import org.jetbrains.annotations.NotNull;

public class SaveLanguage {

    private final IPreferenceRepository preferenceRepository;

    public SaveLanguage(IPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(@NotNull String language) {
        preferenceRepository.saveLanguage(language);
    }

}
