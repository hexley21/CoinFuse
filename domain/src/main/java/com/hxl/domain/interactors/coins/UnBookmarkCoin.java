package com.hxl.domain.interactors.coins;

import com.hxl.domain.repository.CoinRepository;

import io.reactivex.rxjava3.core.Completable;

public class UnBookmarkCoin {

    private final CoinRepository repository;

    public UnBookmarkCoin(CoinRepository repository) {
        this.repository = repository;
    }

    public Completable invoke(String id) {
        return repository.unBookmarkCoin(id);
    }
}
