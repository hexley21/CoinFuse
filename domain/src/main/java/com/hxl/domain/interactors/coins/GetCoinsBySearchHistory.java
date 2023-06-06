package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetCoinsBySearchHistory {

    private final CoinRepository repository;

    @Inject
    public GetCoinsBySearchHistory(CoinRepository repository) {
        this.repository = repository;
    }

    public Single<List<Coin>> invoke() {
        return repository.getCoinsBySearchHistory();
    }
}
