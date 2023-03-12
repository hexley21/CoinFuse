package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.WELCOME;

import com.hxl.domain.repository.PreferenceRepository;

import org.jetbrains.annotations.NotNull;

public class GetWelcome {

    private final PreferenceRepository preferenceRepository;

    public GetWelcome(@NotNull PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final boolean invoke() {
        return preferenceRepository.get(WELCOME.key, WELCOME.def);
    }

}
