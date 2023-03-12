package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.WELCOME;

import com.hxl.domain.repository.PreferenceRepository;

public class SaveWelcome {

    private final PreferenceRepository preferenceRepository;

    public SaveWelcome(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(boolean welcome) {
        preferenceRepository.save(WELCOME.key, welcome);
    }

}
