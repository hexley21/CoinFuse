package com.hxl.domain.interactors.coins;

import com.hxl.domain.repository.CoinRepository;

public class UnBookmarkCoin {

    private final CoinRepository repository;

    public UnBookmarkCoin(CoinRepository repository) {
        this.repository = repository;
    }

    public void invoke(String id) {
        repository.unBookmarkCoin(id);
    }
}
