package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetBookmarkedCoins {

    private final CoinRepository repository;

    public GetBookmarkedCoins(CoinRepository repository) {
        this.repository = repository;
    }

    public Single<List<Coin>> invoke() {
        return repository.getBookmarkedCoins();
    }
}
