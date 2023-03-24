package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class SearchCoins {

    public CoinRepository coinRepository;

    @Inject
    public SearchCoins(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public Single<List<Coin>> invoke(String key) {
        return coinRepository.searchCoins(key);
    }
}
