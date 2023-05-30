package com.hxl.coinfuse.conf.fake.di;

import static com.hxl.coinfuse.conf.fake.FakeDataFactory.getFakeCoins;
import static com.hxl.coinfuse.conf.fake.FakeDataFactory.COIN;
import static com.hxl.coinfuse.conf.fake.FakeDataFactory.getFakeHistory;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.model.Trade;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FakeCoinRepository implements CoinRepository {
    private static final int SIZE = 10;
    @Override
    public Single<List<Coin>> getCoins() {
        return Single.just(getFakeCoins(SIZE));
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        return Single.just(getFakeCoins(SIZE));
    }

    @Override
    public Single<List<Coin>> getCoins(List<String> ids) {
        return Single.just(getFakeCoins(SIZE));
    }

    @Override
    public Single<List<Coin>> searchCoins(String key) {
        return Single.just(getFakeCoins(4));
    }

    @Override
    public Single<Coin> getCoin(String id) {
        return Single.just(COIN);
    }

    @Override
    public Completable bookmarkCoin(String id) {
        return Completable.complete();
    }

    @Override
    public Completable unBookmarkCoin(String id) {
        return Completable.complete();
    }

    @Override
    public Single<Boolean> isCoinBookmarked(String id) {
        return Single.just(true);
    }

    @Override
    public Single<List<Coin>> getBookmarkedCoins() {
        return Single.just(getFakeCoins(SIZE));
    }

    @Override
    public Single<List<Coin>> searchBookmarkedCoins(String query) {
        return null;
    }

    @Override
    public Single<List<Coin>> getCoinsBySearchHistory() {
        return null;
    }

    @Override
    public Completable insertCoinSearchQuery(String query) {
        return null;
    }

    @Override
    public Completable insertCoinSearchQuery(List<String> query) {
        return null;
    }

    @Override
    public Completable deleteCoinSearchQuery(String query) {
        return null;
    }

    @Override
    public Completable eraseCoinSearchHistory() {
        return null;
    }

    @Override
    public Completable eraseBookmarks() {
        return null;
    }

    @Override
    public Completable eraseCoinCache() {
        return null;
    }

    @Override
    public Single<List<CoinPriceHistory>> getCoinPriceHistory(String id, CoinPriceHistory.Interval interval) {
        return Single.just(getFakeHistory(SIZE));
    }

    @Override
    public Single<List<Trade>> getTradesByCoin(String id) {
        return null;
    }

    @Override
    public Single<List<Trade>> getTradesByCoin(String id, int limit, int offset) {
        return null;
    }
}
