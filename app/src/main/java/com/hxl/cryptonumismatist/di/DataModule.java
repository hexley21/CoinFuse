package com.hxl.cryptonumismatist.di;

import android.content.SharedPreferences;

import com.hxl.data.PreferencesRepositoryImpl;
import com.hxl.data.repository.prefs.PreferencesLocal;
import com.hxl.data.repository.prefs.PreferencesSource;
import com.hxl.data.source.PreferencesLocalSource;
import com.hxl.domain.repository.PreferencesRepository;
import com.hxl.local.PreferencesLocalImpl;

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
    PreferencesLocal providePreferencesLocal(SharedPreferences sharedPreferences) {
        return new PreferencesLocalImpl(sharedPreferences);
    }

    @Provides
    @Singleton
    PreferencesSource providePreferenceSource(PreferencesLocal preferencesLocal) {
        return new PreferencesLocalSource(preferencesLocal);
    }

    @Provides
    @Singleton
    PreferencesRepository providePreferenceRepository(PreferencesSource preferencesSource) {
        return new PreferencesRepositoryImpl(preferencesSource);

    }

}
