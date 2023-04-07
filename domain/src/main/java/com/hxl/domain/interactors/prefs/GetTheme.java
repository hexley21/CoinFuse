package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.THEME;

import com.hxl.domain.repository.PreferenceRepository;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class GetTheme {

    private final PreferenceRepository preferenceRepository;

    @Inject
    public GetTheme(@NotNull PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final int invoke() {
        return preferenceRepository.get(THEME.key, THEME.def);
    }

}
