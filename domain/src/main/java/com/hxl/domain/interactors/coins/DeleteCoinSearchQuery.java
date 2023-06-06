package com.hxl.domain.interactors.coins;

import com.hxl.domain.repository.CoinRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class DeleteCoinSearchQuery {
    private final CoinRepository repository;

    @Inject
    public DeleteCoinSearchQuery(CoinRepository repository) {
        this.repository = repository;
    }

    public Completable invoke(String query) {
        return repository.deleteCoinSearchQuery(query);
    }

}
