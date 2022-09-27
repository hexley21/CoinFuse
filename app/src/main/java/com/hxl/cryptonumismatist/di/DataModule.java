package com.hxl.cryptonumismatist.di;

import android.content.Context;

import com.hxl.data.repository.PreferenceRepositoryImpl;
import com.hxl.data.storage.IPreferenceStorage;
import com.hxl.data.storage.sharedprefs.SharedPreferenceStorage;
import com.hxl.domain.repository.IPreferenceRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Provides
    @Singleton
    IPreferenceStorage providePreferenceStorage(@ApplicationContext Context context) {
        return new SharedPreferenceStorage(context);
    }

    @Provides
    @Singleton
    IPreferenceRepository providePreferenceRepository(IPreferenceStorage preferenceStorage) {
        return new PreferenceRepositoryImpl(preferenceStorage);
    }

}
