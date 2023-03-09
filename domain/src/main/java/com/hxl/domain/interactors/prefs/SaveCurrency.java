package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.CURRENCY;

import com.hxl.domain.repository.PreferencesRepository;

import org.jetbrains.annotations.NotNull;

public class SaveCurrency {

    private final PreferencesRepository preferenceRepository;

    public SaveCurrency(PreferencesRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(@NotNull String currency) {
        preferenceRepository.save(CURRENCY.key, currency);
    }

}
