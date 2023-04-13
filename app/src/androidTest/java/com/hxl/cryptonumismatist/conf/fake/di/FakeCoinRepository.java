package com.hxl.cryptonumismatist.conf.fake.di;

import static com.hxl.cryptonumismatist.conf.fake.FakeDataFactory.getFakeCoins;
import static com.hxl.cryptonumismatist.conf.fake.FakeDataFactory.COIN;
import com.hxl.domain.model.Coin;
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
    public Completable saveCoins(List<Coin> coins) {
        return Completable.complete();
    }

    @Override
    public Completable saveCoin(Coin coin) {
        return Completable.complete();
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
}
