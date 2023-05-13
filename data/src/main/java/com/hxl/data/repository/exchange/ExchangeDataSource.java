package com.hxl.data.repository.exchange;

import com.hxl.domain.model.Exchange;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ExchangeDataSource {

    Single<List<Exchange>> getExchanges();
    Single<List<Exchange>> getExchanges(int limit, int offset);
    Single<Exchange> getExchange(String exchangeId);
    Completable insertExchange(List<Exchange> exchanges);
    Completable insertExchange(Exchange exchange);
    Completable eraseExchanges();

}
