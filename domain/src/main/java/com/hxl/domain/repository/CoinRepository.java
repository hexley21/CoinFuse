package com.hxl.domain.repository;

import com.hxl.domain.model.Coin;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface CoinRepository {

    // Remote and Local
    Single<List<Coin>> getCoins();
    Single<List<Coin>> getCoins(int limit, int offset);
    Single<List<Coin>> getCoins(String ids);
    Single<Coin> getCoin(String id);

    // Local
    void saveCoins(List<Coin> coins);
    void bookMarkCoin(String id);
    void unBookMarkCoin(String id);
    Single<List<Coin>> getBookMarkedCoins();
}
