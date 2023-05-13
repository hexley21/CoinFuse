package com.hxl.domain.repository;

import com.hxl.domain.model.Exchange;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ExchangeRepository {

    Single<List<Exchange>> getExchanges();
    Single<List<Exchange>> getExchanges(int limit, int offset);
    Single<Exchange> getExchange(String exchangeId);
    // Local
    Completable insertExchange(List<Exchange> exchanges);
    Completable insertExchange(Exchange exchange);
    Completable eraseExchanges();

}
