package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.LANGUAGE;

import com.hxl.domain.repository.PreferencesRepository;

import org.jetbrains.annotations.NotNull;

public class GetLanguage {

    private final PreferencesRepository preferenceRepository;

    public GetLanguage(@NotNull PreferencesRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @NotNull
    public final String invoke() {
        return preferenceRepository.get(LANGUAGE.key, LANGUAGE.def);
    }

}
