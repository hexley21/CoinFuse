package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.THEME;

import com.hxl.domain.repository.PreferencesRepository;

import org.jetbrains.annotations.NotNull;

public class GetTheme {

    private final PreferencesRepository preferenceRepository;

    public GetTheme(@NotNull PreferencesRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final int invoke() {
        return preferenceRepository.get(THEME.key, THEME.def);
    }

}
