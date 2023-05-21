package com.hxl.remote;

import com.hxl.remote.coin.api.CoinService;
import com.hxl.remote.exchange.api.ExchangeService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ServiceFactory {
    public CoinService createCoin(boolean isDebug, String baseUrl) {
        return createRetrofit(isDebug, baseUrl).create(CoinService.class);
    }

    public ExchangeService createExchange(boolean isDebug, String baseUrl) {
        return createRetrofit(isDebug, baseUrl).create(ExchangeService.class);
    }

    private Retrofit createRetrofit(boolean isDebug, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(createOkHttpClient(loggingInterceptor(isDebug)))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    private OkHttpClient createOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        long TIMEOUT = 60L;
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private HttpLoggingInterceptor loggingInterceptor(boolean isDebug) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (isDebug) {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BASIC);
        } else {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
        }
        return loggingInterceptor;
    }

}
