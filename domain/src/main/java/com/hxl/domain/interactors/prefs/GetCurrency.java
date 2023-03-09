package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.CURRENCY;

import com.hxl.domain.repository.PreferencesRepository;

import org.jetbrains.annotations.NotNull;

public class GetCurrency {

    private final PreferencesRepository preferenceRepository;

    public GetCurrency(@NotNull PreferencesRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @NotNull
    public final String invoke() {
        return preferenceRepository.get(CURRENCY.key, CURRENCY.def);
    }

}
