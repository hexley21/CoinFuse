package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.THEME;

import com.hxl.domain.repository.PreferenceRepository;

import org.jetbrains.annotations.NotNull;

public class GetTheme {

    private final PreferenceRepository preferenceRepository;

    public GetTheme(@NotNull PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final int invoke() {
        return preferenceRepository.get(THEME.key, THEME.def);
    }

}
