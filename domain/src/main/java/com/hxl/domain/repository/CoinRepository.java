package com.hxl.domain.repository;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.Response;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface CoinRepository<T> {

    // Remote and Local
    Single<Response<List<Coin>>> getCoins();
    Single<Response<List<Coin>>> getCoins(int limit, int offset);
    Single<Response<List<Coin>>> getCoins(String ids);
    Single<Response<Coin>> getCoin(String id);

    // Local
    void saveCoins(List<Coin> coins);
    void bookMarkCoin(String id);
    void unBookMarkCoin(String id);
    Single<List<Coin>> getBookMarkedCoins();
}
