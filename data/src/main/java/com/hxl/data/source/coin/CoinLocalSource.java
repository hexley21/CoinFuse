package com.hxl.data.source.coin;

import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.data.repository.coin.CoinSource;
import com.hxl.domain.model.Coin;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class CoinLocalSource implements CoinSource {

    private final CoinLocal local;

    public CoinLocalSource(CoinLocal local) {
        this.local = local;
    }

    @Override
    public Single<List<Coin>> getCoins() {
        return local.getCoins();
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        return local.getCoins(limit, offset);
    }

    @Override
    public Single<List<Coin>> getCoins(String ids) {
        return local.getCoins(ids);
    }

    @Override
    public Single<Coin> getCoin(String id) {
        return local.getCoin(id);
    }

    @Override
    public Completable saveCoins(List<Coin> coins) {
        return local.saveCoins(coins);
    }

    @Override
    public Completable bookmarkCoin(String id) {
        return local.bookmarkCoin(id);
    }

    @Override
    public Completable unBookmarkCoin(String id) {
        return local.unBookmarkCoin(id);
    }

    @Override
    public Single<String> getBookmarkedCoins() {
        return local.getBookmarkedCoins();
    }
}
