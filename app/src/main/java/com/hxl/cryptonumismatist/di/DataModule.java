package com.hxl.cryptonumismatist.di;

import android.content.SharedPreferences;

import com.hxl.data.CoinRepositoryImpl;
import com.hxl.data.PreferenceRepositoryImpl;
import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.data.repository.pref.PreferenceLocal;
import com.hxl.domain.repository.CoinRepository;
import com.hxl.domain.repository.PreferenceRepository;
import com.hxl.local.CoinLocalImpl;
import com.hxl.local.PreferenceLocalImpl;
import com.hxl.local.database.CoinDao;
import com.hxl.local.database.BookmarkDao;
import com.hxl.remote.CoinRemoteImpl;
import com.hxl.remote.api.CoinService;
import com.hxl.remote.model.CoinMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    // region prefs
    @Provides
    @Singleton
    PreferenceLocal providePreferenceLocal(SharedPreferences sharedPreferences) {
        return new PreferenceLocalImpl(sharedPreferences);
    }

    @Provides
    @Singleton
    PreferenceRepository providePreferenceRepository(PreferenceLocal preferenceLocal) {
        return new PreferenceRepositoryImpl(preferenceLocal);

    }
    // endregion

    // region coin
    @Provides
    @Singleton
    CoinRemote provideCoinRemoteSource(CoinService coinService, CoinMapper mapper) {
        return new CoinRemoteImpl(coinService, mapper);
    }

    @Provides
    @Singleton
    CoinLocal provideCoinLocalSource(CoinDao coinDao, BookmarkDao bookmarkDao) {
        return new CoinLocalImpl(coinDao, bookmarkDao);
    }

    @Provides
    @Singleton
    CoinRepository provideCoinRepository(CoinRemote remoteSource, CoinLocal localSource) {
        return new CoinRepositoryImpl(remoteSource, localSource);
    }
    // endregion
}
