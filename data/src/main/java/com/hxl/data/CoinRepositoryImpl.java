package com.hxl.data;

import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.data.repository.coin.CoinSource;
import com.hxl.data.source.coin.CoinSourceFactory;
import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class CoinRepositoryImpl implements CoinRepository {

    private final CoinSource source;
    private final CoinLocal localSource;

    public CoinRepositoryImpl(CoinSourceFactory sourceFactory) {
        source = sourceFactory.getCoinSource(false);
        localSource = sourceFactory.getLocalSource();
    }
    @Override
    public Single<List<Coin>> getCoins() {
        return source.getCoins();
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        return source.getCoins(limit, offset);
    }

    @Override
    public Single<List<Coin>> getCoins(String ids) {
        return source.getCoins(ids);
    }

    @Override
    public Single<Coin> getCoin(String id) {
        return source.getCoin(id);
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
        return localSource.getBookmarkedCoins()
                .flatMap(source::getCoins);
    }

}
