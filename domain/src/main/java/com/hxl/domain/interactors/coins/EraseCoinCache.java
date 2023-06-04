package com.hxl.domain.interactors.coins;

import com.hxl.domain.repository.CoinRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class EraseCoinCache {

    private final CoinRepository repository;

    @Inject
    public EraseCoinCache(CoinRepository coinRepository) {
        this.repository = coinRepository;
    }

    public Completable invoke() {
        return repository.eraseCoinCache();
    }

}
