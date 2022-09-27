package com.hxl.domain.usecase.prefs;

import com.hxl.domain.repository.IPreferenceRepository;

import org.jetbrains.annotations.NotNull;

public class GetCurrency {

    private final IPreferenceRepository preferenceRepository;

    public GetCurrency(@NotNull IPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @NotNull
    public final String invoke() {
        return preferenceRepository.getCurrency();
    }

}
