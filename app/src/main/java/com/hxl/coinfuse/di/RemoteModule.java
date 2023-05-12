package com.hxl.coinfuse.di;

import com.hxl.coinfuse.BuildConfig;
import com.hxl.remote.api.CoinService;
import com.hxl.remote.api.ServiceFactory;
import com.hxl.remote.mapper.CoinDTOMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RemoteModule {

    @Provides
    @Singleton
    public CoinService provideCoinService() {
        return new ServiceFactory().createCoin(BuildConfig.DEBUG, BuildConfig.API_URL);
    }

    @Provides
    @Singleton
    public CoinDTOMapper provideCoinMapper() {
        return new CoinDTOMapper(BuildConfig.ASSET_URL);
    }
}
