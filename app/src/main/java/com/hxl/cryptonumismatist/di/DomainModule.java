package com.hxl.cryptonumismatist.di;

import com.hxl.domain.repository.IPreferenceRepository;
import com.hxl.domain.usecase.prefs.GetCurrency;
import com.hxl.domain.usecase.prefs.GetLanguage;
import com.hxl.domain.usecase.prefs.GetTheme;
import com.hxl.domain.usecase.prefs.GetWelcome;
import com.hxl.domain.usecase.prefs.SaveCurrency;
import com.hxl.domain.usecase.prefs.SaveLanguage;
import com.hxl.domain.usecase.prefs.SaveTheme;
import com.hxl.domain.usecase.prefs.SaveWelcome;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DomainModule {

    // region prefs
    @Provides
    GetWelcome provideGetWelcome(IPreferenceRepository preferenceRepository) {
        return new GetWelcome(preferenceRepository);
    }

    @Provides
    SaveWelcome provideSaveWelcome(IPreferenceRepository preferenceRepository) {
        return new SaveWelcome(preferenceRepository);
    }

    @Provides
    GetLanguage provideGetLanguage(IPreferenceRepository preferenceRepository) {
        return new GetLanguage(preferenceRepository);
    }

    @Provides
    SaveLanguage provideSaveLanguage(IPreferenceRepository preferenceRepository) {
        return new SaveLanguage(preferenceRepository);
    }

    @Provides
    GetTheme provideGetTheme(IPreferenceRepository preferenceRepository) {
        return new GetTheme(preferenceRepository);
    }

    @Provides
    SaveTheme provideSaveTheme(IPreferenceRepository preferenceRepository) {
        return new SaveTheme(preferenceRepository);
    }

    @Provides
    GetCurrency provideGetCurrency(IPreferenceRepository preferenceRepository) {
        return new GetCurrency(preferenceRepository);
    }

    @Provides
    SaveCurrency provideSaveCurrency(IPreferenceRepository preferenceRepository) {
        return new SaveCurrency(preferenceRepository);
    }
    // endregion
}
