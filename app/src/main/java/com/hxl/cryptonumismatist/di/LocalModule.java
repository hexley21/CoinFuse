package com.hxl.cryptonumismatist.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.hxl.cryptonumismatist.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LocalModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        String name = BuildConfig.APPLICATION_ID + ".preferences";
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}
