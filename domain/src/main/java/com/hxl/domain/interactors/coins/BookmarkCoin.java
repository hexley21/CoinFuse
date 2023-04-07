package com.hxl.domain.interactors.coins;

import com.hxl.domain.repository.CoinRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class BookmarkCoin {

    private final CoinRepository repository;

    @Inject
    public BookmarkCoin(CoinRepository repository) {
        this.repository = repository;
    }

    public Completable invoke(String id) {
        return repository.bookmarkCoin(id);
    }
}
