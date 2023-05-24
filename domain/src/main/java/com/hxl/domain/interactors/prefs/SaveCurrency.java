package com.hxl.domain.interactors.prefs;

import com.hxl.domain.repository.PreferenceRepository;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class SaveCurrency {

    private final PreferenceRepository preferenceRepository;

    @Inject
    public SaveCurrency(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(@NotNull String currency) {
        preferenceRepository.save("cur", currency);
    }

}
