package com.hxl.domain.usecase.prefs;

import com.hxl.domain.repository.IPreferenceRepository;

public class SaveWelcome {

    private final IPreferenceRepository preferenceRepository;

    public SaveWelcome(IPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public final void invoke(boolean welcome) {
        preferenceRepository.saveWelcome(welcome);
    }

}
