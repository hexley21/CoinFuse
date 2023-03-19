package com.hxl.data;

import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class CoinRepositoryImpl implements CoinRepository {

    private final CoinRemote remoteSource;
    private final CoinLocal localSource;

    public CoinRepositoryImpl(CoinRemote remoteSource, CoinLocal localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }
    @Override
    public Single<List<Coin>> getCoins() {
        return remoteSource.getCoins().doOnSuccess(this::saveCoins);
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
        return localSource.saveCoins(coins);
    }

    @Override
    public Completable bookmarkCoin(String id) {
        return localSource.bookmarkCoin(id);
    }

    @Override
    public Completable unBookmarkCoin(String id) {
        return localSource.unBookmarkCoin(id);
    }

    @Override
    public Single<List<Coin>> getBookmarkedCoins() {
        return localSource.getBookmarkedCoins();
    }

}
