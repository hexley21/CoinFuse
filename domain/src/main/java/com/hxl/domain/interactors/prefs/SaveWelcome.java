package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.WELCOME;

import com.hxl.domain.repository.PreferenceRepository;

import javax.inject.Inject;

public class SaveWelcome {

    private final PreferenceRepository preferenceRepository;

    @Inject
    public SaveWelcome(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(boolean welcome) {
        preferenceRepository.save(WELCOME.key, welcome);
    }

}
