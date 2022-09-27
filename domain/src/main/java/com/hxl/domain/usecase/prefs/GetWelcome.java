package com.hxl.domain.usecase.prefs;

import com.hxl.domain.repository.IPreferenceRepository;

import org.jetbrains.annotations.NotNull;

public class GetWelcome {

    private final IPreferenceRepository preferenceRepository;

    public GetWelcome(@NotNull IPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final boolean invoke() {
        return preferenceRepository.getWelcome();
    }

}
