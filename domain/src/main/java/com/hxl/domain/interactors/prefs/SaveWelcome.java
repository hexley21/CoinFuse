package com.hxl.domain.interactors.prefs;

import static com.hxl.domain.model.PrefKeys.WELCOME;

import com.hxl.domain.repository.PreferencesRepository;

public class SaveWelcome {

    private final PreferencesRepository preferenceRepository;

    public SaveWelcome(PreferencesRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(boolean welcome) {
        preferenceRepository.save(WELCOME.key, welcome);
    }

}
