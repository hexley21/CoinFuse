package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.LANGUAGE;

import com.hxl.domain.repository.PreferenceRepository;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class GetLanguage {

    private final PreferenceRepository preferenceRepository;

    @Inject
    public GetLanguage(@NotNull PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @NotNull
    public final String invoke() {
        return preferenceRepository.get(LANGUAGE.key, LANGUAGE.def);
    }

}
