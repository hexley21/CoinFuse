package com.hxl.data.source.exchange;

import com.hxl.data.repository.exchange.ExchangeDataSource;
import com.hxl.data.repository.exchange.ExchangeLocal;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class ExchangeLocalSource implements ExchangeDataSource {

    private final ExchangeLocal local;

    @Inject
    public ExchangeLocalSource(ExchangeLocal local) {
        this.local = local;
    }

    @Override
    public Single<List<Exchange>> getExchanges() {
        return local.getExchanges();
    }

    @Override
    public Single<List<Exchange>> getExchanges(int limit, int offset) {
        return local.getExchanges(limit, offset);
    }

    @Override
    public Single<Exchange> getExchange(String exchangeId) {
        return local.getExchange(exchangeId);
    }

    @Override
    public Single<List<Trade>> getTrades(Map<String, String> queryMap) {
        throw new UnsupportedOperationException("getTrades is not supported for LocalSource");
    }

    @Override
    public Completable insertExchange(List<Exchange> exchanges) {
        return local.insertExchange(exchanges.toArray(new Exchange[0]));
    }

    @Override
    public Completable insertExchange(Exchange exchange) {
        return local.insertExchange(exchange);
    }

    @Override
    public Completable eraseExchanges() {
        return local.eraseExchanges();
    }
}
