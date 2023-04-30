package com.hxl.domain.interactors.coins;

import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class InsertCoinSearchQuery {

    private final CoinRepository repository;

    @Inject
    public InsertCoinSearchQuery(CoinRepository repository) {
        this.repository = repository;
    }

    public Completable invoke(String query) {
        return repository.insertCoinSearchQuery(query);
    }

    public Completable invoke(List<String> query) {
        return repository.insertCoinSearchQuery(query);
    }

}
