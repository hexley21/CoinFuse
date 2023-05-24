package com.hxl.domain.interactors.prefs;

import com.hxl.domain.repository.PreferenceRepository;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class GetCurrency {

    private final PreferenceRepository preferenceRepository;

    @Inject
    public GetCurrency(@NotNull PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @NotNull
    public final String invoke() {
        return preferenceRepository.get("cur", "usd");
    }

}
