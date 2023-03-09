package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.WELCOME;

import com.hxl.domain.repository.PreferencesRepository;

import org.jetbrains.annotations.NotNull;

public class GetWelcome {

    private final PreferencesRepository preferenceRepository;

    public GetWelcome(@NotNull PreferencesRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final boolean invoke() {
        return preferenceRepository.get(WELCOME.key, WELCOME.def);
    }

}
