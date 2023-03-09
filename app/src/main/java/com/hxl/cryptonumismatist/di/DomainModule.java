package com.hxl.cryptonumismatist.di;


import com.hxl.domain.interactors.prefs.GetCurrency;
import com.hxl.domain.interactors.prefs.GetLanguage;
import com.hxl.domain.interactors.prefs.GetTheme;
import com.hxl.domain.interactors.prefs.GetWelcome;
import com.hxl.domain.interactors.prefs.SaveCurrency;
import com.hxl.domain.interactors.prefs.SaveLanguage;
import com.hxl.domain.interactors.prefs.SaveTheme;
import com.hxl.domain.interactors.prefs.SaveWelcome;
import com.hxl.domain.repository.PreferencesRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DomainModule {

    // region prefs
    @Provides
    GetCurrency provideGetCurrency(PreferencesRepository preferenceRepository) {
        return new GetCurrency(preferenceRepository);
    }
    @Provides
    GetLanguage provideGetLanguage(PreferencesRepository preferenceRepository) {
        return new GetLanguage(preferenceRepository);
    }
    @Provides
    GetTheme provideGetTheme(PreferencesRepository preferenceRepository) {
        return new GetTheme(preferenceRepository);
    }
    @Provides
    GetWelcome provideGetWelcome(PreferencesRepository preferenceRepository) {
        return new GetWelcome(preferenceRepository);
    }

    @Provides
    SaveCurrency provideSaveCurrency(PreferencesRepository preferenceRepository) {
        return new SaveCurrency(preferenceRepository);
    }
    @Provides
    SaveLanguage provideSaveLanguage(PreferencesRepository preferenceRepository) {
        return new SaveLanguage(preferenceRepository);
    }
    @Provides
    SaveTheme provideSaveTheme(PreferencesRepository preferenceRepository) {
        return new SaveTheme(preferenceRepository);
    }
    @Provides
    SaveWelcome provideSaveWelcome(PreferencesRepository preferenceRepository) {
        return new SaveWelcome(preferenceRepository);
    }
    // endregion
}
