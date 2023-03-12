package com.hxl.cryptonumismatist.di;


import com.hxl.domain.interactors.prefs.GetCurrency;
import com.hxl.domain.interactors.prefs.GetLanguage;
import com.hxl.domain.interactors.prefs.GetTheme;
import com.hxl.domain.interactors.prefs.GetWelcome;
import com.hxl.domain.interactors.prefs.SaveCurrency;
import com.hxl.domain.interactors.prefs.SaveLanguage;
import com.hxl.domain.interactors.prefs.SaveTheme;
import com.hxl.domain.interactors.prefs.SaveWelcome;
import com.hxl.domain.repository.PreferenceRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DomainModule {

    // region prefs
    @Provides
    GetCurrency provideGetCurrency(PreferenceRepository preferenceRepository) {
        return new GetCurrency(preferenceRepository);
    }
    @Provides
    GetLanguage provideGetLanguage(PreferenceRepository preferenceRepository) {
        return new GetLanguage(preferenceRepository);
    }
    @Provides
    GetTheme provideGetTheme(PreferenceRepository preferenceRepository) {
        return new GetTheme(preferenceRepository);
    }
    @Provides
    GetWelcome provideGetWelcome(PreferenceRepository preferenceRepository) {
        return new GetWelcome(preferenceRepository);
    }

    @Provides
    SaveCurrency provideSaveCurrency(PreferenceRepository preferenceRepository) {
        return new SaveCurrency(preferenceRepository);
    }
    @Provides
    SaveLanguage provideSaveLanguage(PreferenceRepository preferenceRepository) {
        return new SaveLanguage(preferenceRepository);
    }
    @Provides
    SaveTheme provideSaveTheme(PreferenceRepository preferenceRepository) {
        return new SaveTheme(preferenceRepository);
    }
    @Provides
    SaveWelcome provideSaveWelcome(PreferenceRepository preferenceRepository) {
        return new SaveWelcome(preferenceRepository);
    }
    // endregion
}
