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
import com.hxl.local.database.coin.BookmarkDao;
import com.hxl.local.database.coin.CoinDao;
import com.hxl.remote.CoinRemoteImpl;
import com.hxl.remote.api.CoinService;
import com.hxl.remote.mapper.CoinDTOMapper;

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
    CoinRemote provideCoinRemoteSource(CoinService coinService, CoinDTOMapper mapper) {
        return new CoinRemoteImpl(coinService, mapper);
    }

    @Provides
    @Singleton
    CoinLocal provideCoinLocalSource(CoinDao coinDao, BookmarkDao bookmarkDao) {
        return new CoinLocalImpl(coinDao, bookmarkDao);
    }

    @Provides
    @Singleton
    CoinRepository provideCoinRepository(CoinRemote coinRemote, CoinLocal coinLocal) {
        return new CoinRepositoryImpl(coinRemote, coinLocal);
    }
    // endregion
}
