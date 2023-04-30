package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.SearchQuery;
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

    public Single<List<SearchQuery>> invoke() {
        return repository.getCoinSearchHistory();
    }
}
