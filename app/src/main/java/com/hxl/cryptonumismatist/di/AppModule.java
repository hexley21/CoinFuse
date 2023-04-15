package com.hxl.cryptonumismatist.di;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hxl.cryptonumismatist.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    RequestManager provideGlide(@ApplicationContext Context context) {
        return Glide.with(context).setDefaultRequestOptions(
                new RequestOptions()
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
        );
    }
}
