package com.hxl.data;

import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class CoinRepositoryImpl implements CoinRepository {

    private final CoinRemote remoteSource;

    public CoinRepositoryImpl(CoinRemote source) {
        this.remoteSource = source;
    }
    @Override
    public Single<List<Coin>> getCoins() {
        return remoteSource.getCoins();
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        return remoteSource.getCoins(limit, offset);
    }

    @Override
    public Single<List<Coin>> getCoins(String ids) {
        return remoteSource.getCoins(ids);
    }

    @Override
    public Single<Coin> getCoin(String id) {
        return remoteSource.getCoin(id);
    }

    @Override
    public Completable saveCoins(List<Coin> coins) {
        throw new UnsupportedOperationException("saveCoins is not supported yet");
    }

    @Override
    public Completable bookmarkCoin(String id) {
        throw new UnsupportedOperationException("bookMarkCoin is not supported yet");
    }

    @Override
    public Completable unBookmarkCoin(String id) {
        throw new UnsupportedOperationException("unBookMarkCoin is not supported yet");
    }

    @Override
    public Single<List<Coin>> getBookmarkedCoins() {
        throw new UnsupportedOperationException("getBookMarkedCoins is not supported yet");
    }

}
