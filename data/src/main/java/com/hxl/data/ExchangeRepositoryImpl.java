package com.hxl.data;

import com.hxl.data.repository.PingUtil;
import com.hxl.data.source.exchange.ExchangeSourceFactory;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.repository.ExchangeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class ExchangeRepositoryImpl implements ExchangeRepository {

    private final ExchangeSourceFactory sourceFactory;

    @Inject
    public ExchangeRepositoryImpl(ExchangeSourceFactory sourceFactory) {
        this.sourceFactory = sourceFactory;
    }

    @Override
    public Single<List<Exchange>> getExchanges() {
        return sourceFactory.getDataSource(PingUtil.isOnline()).getExchanges();
    }

    @Override
    public Single<List<Exchange>> getExchanges(int limit, int offset) {
        return sourceFactory.getDataSource(PingUtil.isOnline()).getExchanges(limit, offset);
    }

    @Override
    public Single<Exchange> getExchange(String exchangeId) {
        return sourceFactory.getDataSource(PingUtil.isOnline()).getExchange(exchangeId);
    }

    @Override
    public Completable insertExchange(List<Exchange> exchanges) {
        return sourceFactory.getLocal().insertExchange(exchanges);
    }

    @Override
    public Completable insertExchange(Exchange exchange) {
        return sourceFactory.getLocal().insertExchange(exchange);
    }

    @Override
    public Completable eraseExchanges() {
        return sourceFactory.getLocal().eraseExchanges();
    }
}
