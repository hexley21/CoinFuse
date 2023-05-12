package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.ValueAndTimestamp;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetCoinSearchHistory {

    private final CoinRepository repository;

    @Inject
    public GetCoinSearchHistory(CoinRepository repository) {
        this.repository = repository;
    }

    public Single<List<ValueAndTimestamp<String>>> invoke() {
        return repository.getCoinSearchHistoryValues();
    }
}
