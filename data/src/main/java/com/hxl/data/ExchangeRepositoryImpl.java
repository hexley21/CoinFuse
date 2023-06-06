package com.hxl.data;

import com.hxl.data.repository.exchange.ExchangeLocal;
import com.hxl.data.repository.exchange.ExchangeRemote;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;
import com.hxl.domain.repository.ExchangeRepository;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class ExchangeRepositoryImpl implements ExchangeRepository {

    private final ExchangeRemote remote;
    private final ExchangeLocal local;

    @Inject
    public ExchangeRepositoryImpl(ExchangeRemote exchangeRemote, ExchangeLocal exchangeLocal) {
        this.remote = exchangeRemote;
        this.local = exchangeLocal;
    }

    @Override
    public Single<List<Exchange>> getExchanges() {
        return remote.getExchanges()
                .flatMap(x -> insertExchange(x).toSingleDefault(x))
                .onErrorResumeWith(local.getExchanges())
                .map(ex -> {
                    if (ex.isEmpty())
                        throw new UnknownHostException("No cached data, connect to the internet");
                    return ex;
                });
    }

    @Override
    public Single<List<Exchange>> getExchanges(int limit, int offset) {
        return remote.getExchanges(limit, offset)
                .flatMap(x -> insertExchange(x).toSingleDefault(x))
                .onErrorResumeWith(local.getExchanges(limit, offset))
                .map(ex -> {
                    if (ex.isEmpty()) {
                        throw new UnknownHostException("No cached data, connect to the internet");
                    }
                    return ex;
                });
    }

    @Override
    public Single<Exchange> getExchange(String exchangeId) {
        return remote.getExchange(exchangeId)
                .flatMap(x -> insertExchange(x).toSingleDefault(x))
                .onErrorResumeWith(local.getExchange(exchangeId))
                .map(ex -> {
                    if (ex == null) {
                        throw new UnknownHostException("No cached data, connect to the internet");
                    }
                    return ex;
                });
    }

    @Override
    public Single<List<Trade>> getTrades(Map<String, String> queryMap) {
        return remote.getTrades(queryMap);
    }

    private Completable insertExchange(List<Exchange> exchanges) {
        return local.insertExchange(exchanges.toArray(new Exchange[0]));
    }

    private Completable insertExchange(Exchange exchange) {
        return local.insertExchange(exchange);
    }

    @Override
    public Completable eraseExchanges() {
        return local.eraseExchanges();
    }
}
