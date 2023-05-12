package com.hxl.domain.repository;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.model.ValueAndTimestamp;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CoinRepository {

    // Remote and Local
    Single<List<Coin>> getCoins();
    Single<List<Coin>> getCoins(int limit, int offset);
    Single<List<Coin>> getCoins(List<String> ids);
    Single<List<Coin>> searchCoins(String key);
    Single<Coin> getCoin(String id);

    // Local
    Completable saveCoins(List<Coin> coins);
    Completable saveCoin(Coin coin);
    Completable bookmarkCoin(String id);
    Completable unBookmarkCoin(String id);
    Single<Boolean> isCoinBookmarked(String id);
    Single<List<Coin>> getBookmarkedCoins();
    Single<List<Coin>> searchBookmarkedCoins(String query);
    Single<List<Coin>> getCoinsBySearchHistory();
    Completable insertCoinSearchQuery(String query);
    Completable insertCoinSearchQuery(List<String> query);
    Completable deleteCoinSearchQuery(String query);
    Completable deleteCoinSearchHistory();

    // Remote
    Single<List<CoinPriceHistory>> getCoinPriceHistory(String id, CoinPriceHistory.Interval interval);
}
