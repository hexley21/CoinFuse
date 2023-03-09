package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.LANGUAGE;

import com.hxl.domain.repository.PreferencesRepository;

import org.jetbrains.annotations.NotNull;

public class SaveLanguage {

    private final PreferencesRepository preferenceRepository;

    public SaveLanguage(PreferencesRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(@NotNull String language) {
        preferenceRepository.save(LANGUAGE.key, language);
    }

}
