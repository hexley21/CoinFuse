package com.hxl.coinfuse.di;

import android.content.SharedPreferences;

import com.hxl.data.CoinRepositoryImpl;
import com.hxl.data.ExchangeRepositoryImpl;
import com.hxl.data.PreferenceRepositoryImpl;
import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.data.repository.exchange.ExchangeLocal;
import com.hxl.data.repository.exchange.ExchangeRemote;
import com.hxl.data.repository.pref.PreferenceLocal;
import com.hxl.data.source.exchange.ExchangeLocalSource;
import com.hxl.data.source.exchange.ExchangeRemoteSource;
import com.hxl.data.source.exchange.ExchangeSourceFactory;
import com.hxl.domain.repository.CoinRepository;
import com.hxl.domain.repository.ExchangeRepository;
import com.hxl.domain.repository.PreferenceRepository;
import com.hxl.local.CoinLocalImpl;
import com.hxl.local.ExchangeLocalImpl;
import com.hxl.local.PreferenceLocalImpl;
import com.hxl.local.coin.dao.BookmarkDao;
import com.hxl.local.coin.dao.CoinDao;
import com.hxl.local.coin.dao.CoinSearchDao;
import com.hxl.local.exchange.dao.ExchangeDao;
import com.hxl.remote.coin.CoinRemoteImpl;
import com.hxl.remote.coin.api.CoinService;
import com.hxl.remote.coin.mapper.CoinDTOMapper;
import com.hxl.remote.exchange.ExchangeRemoteImpl;
import com.hxl.remote.exchange.api.ExchangeService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    // region Prefs
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

    // region Coin
    @Provides
    @Singleton
    CoinRemote provideCoinRemoteSource(CoinService coinService, CoinDTOMapper mapper) {
        return new CoinRemoteImpl(coinService, mapper);
    }

    @Provides
    @Singleton
    CoinLocal provideCoinLocalSource(CoinDao coinDao, BookmarkDao bookmarkDao, CoinSearchDao coinSearchDao) {
        return new CoinLocalImpl(coinDao, bookmarkDao, coinSearchDao);
    }

    @Provides
    @Singleton
    CoinRepository provideCoinRepository(CoinRemote coinRemote, CoinLocal coinLocal) {
        return new CoinRepositoryImpl(coinRemote, coinLocal);
    }
    // endregion

    // region Exchange

    @Provides
    @Singleton
    ExchangeRemote provideExchangeRemoteSource(ExchangeService service) {
        return new ExchangeRemoteImpl(service);
    }

    @Provides
    @Singleton
    ExchangeLocal provideExchangeLocalSource(ExchangeDao dao) {
        return new ExchangeLocalImpl(dao);
    }

    @Provides
    @Singleton
    ExchangeSourceFactory providesExchangeSourceFactory(ExchangeRemote remote, ExchangeLocal local) {
        return new ExchangeSourceFactory(
                new ExchangeRemoteSource(remote),
                new ExchangeLocalSource(local)
        );
    }

    @Provides
    @Singleton
    ExchangeRepository providesExchangeRepository(ExchangeSourceFactory sourceFactory) {
        return new ExchangeRepositoryImpl(sourceFactory);
    }

    // endregion
}
