package com.hxl.data.repository.exchange;

import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ExchangeDataSource {

    Single<List<Exchange>> getExchanges();
    Single<List<Exchange>> getExchanges(int limit, int offset);
    Single<Exchange> getExchange(String exchangeId);
    Single<List<Trade>> getTrades(Map<String, String> queryMap);
    Completable insertExchange(List<Exchange> exchanges);
    Completable insertExchange(Exchange exchange);
    Completable eraseExchanges();

}
