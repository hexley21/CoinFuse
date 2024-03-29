package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetCoin {

    private final CoinRepository repository;

    @Inject
    public GetCoin(CoinRepository repository) {
        this.repository = repository;
    }

    public Single<Coin> invoke(String id) {
        return repository.getCoin(id);
    }
}
