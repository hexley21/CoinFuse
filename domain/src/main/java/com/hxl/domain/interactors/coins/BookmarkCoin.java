package com.hxl.domain.interactors.coins;

import com.hxl.domain.repository.CoinRepository;

public class BookmarkCoin {

    private final CoinRepository repository;

    public BookmarkCoin(CoinRepository repository) {
        this.repository = repository;
    }

    public void invoke(String id) {
        repository.bookmarkCoin(id);
    }
}
