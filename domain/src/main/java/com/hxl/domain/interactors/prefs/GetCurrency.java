package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.CURRENCY;

import com.hxl.domain.repository.PreferenceRepository;

import org.jetbrains.annotations.NotNull;

public class GetCurrency {

    private final PreferenceRepository preferenceRepository;

    public GetCurrency(@NotNull PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @NotNull
    public final String invoke() {
        return preferenceRepository.get(CURRENCY.key, CURRENCY.def);
    }

}
