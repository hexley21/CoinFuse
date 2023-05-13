package com.hxl.data.source.exchange;

import com.hxl.data.repository.exchange.ExchangeDataSource;

import javax.inject.Inject;

public class ExchangeSourceFactory {

    private final ExchangeRemoteSource remoteSource;
    private final ExchangeLocalSource localSource;


    @Inject
    public ExchangeSourceFactory(ExchangeRemoteSource remoteSource, ExchangeLocalSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public ExchangeDataSource getDataSource(boolean isOnline) {
        if (isOnline)
            return getRemote();
        return getLocal();
    }

    public ExchangeDataSource getRemote() {
        return remoteSource;
    }

    public ExchangeDataSource getLocal() {
        return localSource;
    }
}
