package com.hxl.data.repository.coin;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.ValueAndTimestamp;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CoinLocal {
    // Remote and Local
    Single<List<Coin>> getCoins();
    Single<List<Coin>> getCoins(int limit, int offset);
    Single<List<Coin>> getCoins(List<String> ids);
    Single<List<Coin>> searchCoins(String key);
    Single<Coin> getCoin(String id);

    // Local
    Completable saveCoins(Coin... coins);
    Completable bookmarkCoin(String id);
    Completable unBookmarkCoin(String id);
    Single<Boolean> isCoinBookmarked(String id);
    Single<List<Coin>> getBookmarkedCoins();
    Single<List<ValueAndTimestamp<String>>> getBookmarkedCoinIds();
    Single<List<ValueAndTimestamp<String>>> getCoinSearchHistory();
    Completable insertCoinSearchQuery(String query);
    Completable insertCoinSearchQuery(String... query);
    Completable deleteCoinSearchQuery(String query);
    Completable eraseCoinSearchHistory();
    Completable eraseBookmarks();
    Completable eraseCoinCache();
}
