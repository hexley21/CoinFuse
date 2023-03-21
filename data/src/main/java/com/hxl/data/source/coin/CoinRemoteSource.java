package com.hxl.data.source.coin;

import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.data.repository.coin.CoinSource;
import com.hxl.domain.model.Coin;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class CoinRemoteSource implements CoinSource {

    private final CoinRemote remote;

    public CoinRemoteSource(CoinRemote remote) {
        this.remote = remote;
    }

    @Override
    public Single<List<Coin>> getCoins() {
        return remote.getCoins();
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        return remote.getCoins(limit, offset);
    }

    @Override
    public Single<List<Coin>> getCoins(String ids) {
        return remote.getCoins(ids);
    }

    @Override
    public Single<Coin> getCoin(String id) {
        return remote.getCoin(id);
    }

    @Override
    public Completable saveCoins(List<Coin> coins) {
        throw new UnsupportedOperationException("saveCoins() is not supported on remote coin source");
    }

    @Override
    public Completable bookmarkCoin(String id) {
        throw new UnsupportedOperationException("bookmarkCoin() is not supported on remote coin source");
    }

    @Override
    public Completable unBookmarkCoin(String id) {
        throw new UnsupportedOperationException("unBookmarkCoin() is not supported on remote coin source");
    }

    @Override
    public Single<String> getBookmarkedCoins() {
        throw new UnsupportedOperationException("getBookmarkedCoins() is not supported on remote coin source");
    }
}
