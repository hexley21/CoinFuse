package com.hxl.cryptonumismatist.di;

import android.content.SharedPreferences;

import com.hxl.data.PreferenceRepositoryImpl;
import com.hxl.data.repository.pref.PreferenceSource;
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
    PreferenceSource providePreferenceSource(SharedPreferences sharedPreferences) {
        return new PreferenceLocalImpl(sharedPreferences);
    }

    @Provides
    @Singleton
    PreferenceRepository providePreferenceRepository(PreferenceSource preferenceSource) {
        return new PreferenceRepositoryImpl(preferenceSource);

    }

}
