package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetCoins {
    private final CoinRepository repository;

    @Inject
    public GetCoins(CoinRepository repository) {
        this.repository = repository;
    }

    public Single<List<Coin>> invoke() {
        return repository.getCoins();
    }

    public Single<List<Coin>> invoke(List<String> ids) {
        return repository.getCoins(ids);
    }

    public Single<List<Coin>> invoke(int limit, int offset) {
        return repository.getCoins(limit, offset);
    }
}
