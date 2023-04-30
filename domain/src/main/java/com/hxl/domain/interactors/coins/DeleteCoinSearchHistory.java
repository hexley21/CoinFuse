package com.hxl.domain.interactors.coins;

import com.hxl.domain.repository.CoinRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class DeleteCoinSearchHistory {

    private final CoinRepository repository;

    @Inject
    public DeleteCoinSearchHistory(CoinRepository repository) {
        this.repository = repository;
    }

    public Completable invoke() {
        return repository.deleteCoinSearchHistory();
    }
}
