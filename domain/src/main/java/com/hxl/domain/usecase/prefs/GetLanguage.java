package com.hxl.domain.usecase.prefs;

import com.hxl.domain.repository.IPreferenceRepository;

import org.jetbrains.annotations.NotNull;

public class GetLanguage {

    private final IPreferenceRepository preferenceRepository;

    public GetLanguage(@NotNull IPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @NotNull
    public final String invoke() {
        return preferenceRepository.getLanguage();
    }

}
