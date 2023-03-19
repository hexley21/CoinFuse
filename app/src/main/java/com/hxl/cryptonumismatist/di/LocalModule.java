package com.hxl.cryptonumismatist.di;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.hxl.cryptonumismatist.BuildConfig;
import com.hxl.local.database.CoinDao;
import com.hxl.local.database.CoinDatabase;
import com.hxl.local.database.FavouriteDao;

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
    CoinDatabase provideCoinDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                CoinDatabase.class,
                CoinDatabase.DB_NAME
        ).build();
    }

    @Provides
    @Singleton
    CoinDao provideCoinDao(@NonNull CoinDatabase coinDatabase) {
        return coinDatabase.coinDao();
    }

    @Provides
    @Singleton
    FavouriteDao provideFavouriteDao(@NonNull CoinDatabase coinDatabase) {
        return coinDatabase.favouriteDao();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        String name = BuildConfig.APPLICATION_ID + ".preferences";
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}
