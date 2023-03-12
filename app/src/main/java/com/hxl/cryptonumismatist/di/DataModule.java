package com.hxl.cryptonumismatist.di;

import android.content.SharedPreferences;

import com.hxl.data.PreferenceRepositoryImpl;
import com.hxl.data.repository.prefs.PreferenceLocal;
import com.hxl.data.repository.prefs.PreferenceSource;
import com.hxl.data.source.PreferenceLocalSource;
import com.hxl.domain.repository.PreferenceRepository;
import com.hxl.local.PreferenceLocalImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Provides
    @Singleton
    PreferenceLocal providePreferencesLocal(SharedPreferences sharedPreferences) {
        return new PreferenceLocalImpl(sharedPreferences);
    }

    @Provides
    @Singleton
    PreferenceSource providePreferenceSource(PreferenceLocal preferenceLocal) {
        return new PreferenceLocalSource(preferenceLocal);
    }

    @Provides
    @Singleton
    PreferenceRepository providePreferenceRepository(PreferenceSource preferenceSource) {
        return new PreferenceRepositoryImpl(preferenceSource);

    }

}
