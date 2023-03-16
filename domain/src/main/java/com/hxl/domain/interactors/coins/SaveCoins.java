package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

public class SaveCoins {

    private final CoinRepository repository;

    public SaveCoins(CoinRepository repository) {
        this.repository = repository;
    }

    public void invoke(List<Coin> coins) {
        this.repository.saveCoins(coins);
    }
}
