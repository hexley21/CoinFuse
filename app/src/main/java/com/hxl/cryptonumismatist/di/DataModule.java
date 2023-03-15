package com.hxl.cryptonumismatist.di;

import android.content.SharedPreferences;

import com.hxl.data.CoinRepositoryImpl;
import com.hxl.data.PreferenceRepositoryImpl;
import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.data.repository.pref.PreferenceLocal;
import com.hxl.domain.repository.CoinRepository;
import com.hxl.domain.repository.PreferenceRepository;
import com.hxl.local.PreferenceLocalImpl;
import com.hxl.remote.CoinRemoteImpl;
import com.hxl.remote.api.CoinService;

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
    CoinRemote provideCoinRemoteSource(CoinService coinService) {
        return new CoinRemoteImpl(coinService);
    }

    @Provides
    @Singleton
    CoinRepository provideCoinRepository(CoinRemote remoteSource) {
        return new CoinRepositoryImpl(remoteSource);
    }
    // endregion
}
