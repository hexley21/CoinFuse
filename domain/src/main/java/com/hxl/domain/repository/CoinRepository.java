package com.hxl.domain.repository;

import com.hxl.domain.model.Coin;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CoinRepository {

    // Remote and Local
    Single<List<Coin>> getCoins();
    Single<List<Coin>> getCoins(int limit, int offset);
    Single<List<Coin>> getCoins(List<String> ids);
    Single<Coin> getCoin(String id);

    // Local
    Completable saveCoins(List<Coin> coins);
    Completable saveCoin(Coin coin);
    Completable bookmarkCoin(String id);
    Completable unBookmarkCoin(String id);
    Single<List<Coin>> getBookmarkedCoins();
}
