package com.hxl.cryptonumismatist.di;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.hxl.cryptonumismatist.BuildConfig;
import com.hxl.local.database.AppDatabase;
import com.hxl.local.database.coin.CoinDao;
import com.hxl.local.database.coin.BookmarkDao;
import com.hxl.local.database.coin.CoinSearchDao;

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
    AppDatabase provideCoinDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                AppDatabase.DB_NAME
        ).build();
    }

    @Provides
    @Singleton
    CoinDao provideCoinDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.coinDao();
    }

    @Provides
    @Singleton
    BookmarkDao provideBookmarkDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.bookmarkDao();
    }

    @Provides
    @Singleton
    CoinSearchDao provideCoinSearchDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.coinSearchDao();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        String name = BuildConfig.APPLICATION_ID + ".preferences";
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}
