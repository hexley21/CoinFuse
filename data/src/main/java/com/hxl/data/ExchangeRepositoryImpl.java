package com.hxl.data;

import com.hxl.data.util.PingUtil;
import com.hxl.data.source.exchange.ExchangeSourceFactory;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;
import com.hxl.domain.repository.ExchangeRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ExchangeRepositoryImpl implements ExchangeRepository {

    private final ExchangeSourceFactory sourceFactory;

    @Inject
    public ExchangeRepositoryImpl(ExchangeSourceFactory sourceFactory) {
        this.sourceFactory = sourceFactory;
    }

    @Override
    public Single<List<Exchange>> getExchanges() {
        boolean isOnline = PingUtil.isOnline();
        Single<List<Exchange>> exchanges = sourceFactory.getDataSource(isOnline).getExchanges()
                .subscribeOn(Schedulers.io());
        if (isOnline)
            return exchanges.flatMap(x -> insertExchange(x).toSingleDefault(x));
        return exchanges;
    }

    @Override
    public Single<List<Exchange>> getExchanges(int limit, int offset) {
        boolean isOnline = PingUtil.isOnline();
        Single<List<Exchange>> exchanges = sourceFactory.getDataSource(isOnline).getExchanges(limit, offset)
                .subscribeOn(Schedulers.io());
        if (isOnline)
            return exchanges.flatMap(x -> insertExchange(x).toSingleDefault(x));
        return exchanges;
    }

    @Override
    public Single<Exchange> getExchange(String exchangeId) {
        boolean isOnline = PingUtil.isOnline();
        Single<Exchange> exchange = sourceFactory.getDataSource(isOnline).getExchange(exchangeId)
                .subscribeOn(Schedulers.io());
        if (isOnline)
            return exchange.flatMap(x -> insertExchange(x).toSingleDefault(x));
        return exchange;
    }

    @Override
    public Single<List<Trade>> getTrades(Map<String, String> queryMap) {
        return sourceFactory.getRemote().getTrades(queryMap);
    }

    @Override
    public Completable insertExchange(List<Exchange> exchanges) {
        return sourceFactory.getLocal().insertExchange(exchanges)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable insertExchange(Exchange exchange) {
        return sourceFactory.getLocal().insertExchange(exchange)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable eraseExchanges() {
        return sourceFactory.getLocal().eraseExchanges()
                .subscribeOn(Schedulers.io());
    }
}
