package com.hxl.data.source.exchange;

import com.hxl.data.repository.exchange.ExchangeDataSource;
import com.hxl.data.repository.exchange.ExchangeRemote;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class ExchangeRemoteSource implements ExchangeDataSource {

    private final ExchangeRemote remote;

    @Inject
    public ExchangeRemoteSource(ExchangeRemote remote) {
        this.remote = remote;
    }

    @Override
    public Single<List<Exchange>> getExchanges() {
        return remote.getExchanges();
    }

    @Override
    public Single<List<Exchange>> getExchanges(int limit, int offset) {
        return remote.getExchanges(limit, offset);
    }

    @Override
    public Single<Exchange> getExchange(String exchangeId) {
        return remote.getExchange(exchangeId);
    }

    @Override
    public Single<List<Trade>> getTrades(Map<String, String> queryMap) {
        return remote.getTrades(queryMap);
    }

    @Override
    public Completable insertExchange(List<Exchange> exchanges) {
        throw new UnsupportedOperationException("insertExchange is not supported for RemoteSource");
    }

    @Override
    public Completable insertExchange(Exchange exchange) {
        throw new UnsupportedOperationException("insertExchange is not supported for RemoteSource");
    }

    @Override
    public Completable eraseExchanges() {
        throw new UnsupportedOperationException("eraseExchanges() is not supported for RemoteSource");
    }
}
