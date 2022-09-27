package com.hxl.domain.usecase.prefs;

import com.hxl.domain.repository.IPreferenceRepository;

import org.jetbrains.annotations.NotNull;

public class GetTheme {

    private final IPreferenceRepository preferenceRepository;

    public GetTheme(@NotNull IPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final int invoke() {
        return preferenceRepository.getTheme();
    }

}
