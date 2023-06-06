package com.hxl.domain.repository;

import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ExchangeRepository {

    Single<List<Exchange>> getExchanges();
    Single<List<Exchange>> getExchanges(int limit, int offset);
    Single<Exchange> getExchange(String exchangeId);
    // Remote
    Single<List<Trade>> getTrades(Map<String, String> queryMap);
    // Local
    Completable eraseExchanges();
 }
