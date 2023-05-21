package com.hxl.data.repository.exchange;

import com.hxl.domain.model.Exchange;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ExchangeLocal {

    Single<List<Exchange>> getExchanges();
    Single<List<Exchange>> getExchanges(int limit, int offset);
    Single<Exchange> getExchange(String exchangeId);
    Completable insertExchange(Exchange... exchanges);
    Completable eraseExchanges();

}
