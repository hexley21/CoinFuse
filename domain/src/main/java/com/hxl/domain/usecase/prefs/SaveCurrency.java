package com.hxl.domain.usecase.prefs;

import com.hxl.domain.repository.IPreferenceRepository;

import org.jetbrains.annotations.NotNull;

public class SaveCurrency {

    private final IPreferenceRepository preferenceRepository;

    public SaveCurrency(IPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(@NotNull String currency) {
        preferenceRepository.saveCurrency(currency);
    }

}
