package com.hxl.domain.interactors.exchanges;

import com.hxl.domain.model.Trade;
import com.hxl.domain.repository.ExchangeRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetTrades {

    private final ExchangeRepository repository;

    @Inject
    public GetTrades(ExchangeRepository repository) {
        this.repository = repository;
    }

    public Single<List<Trade>> invoke(Map<String, String> queryMap) {
        return repository.getTrades(queryMap);
    }
}
